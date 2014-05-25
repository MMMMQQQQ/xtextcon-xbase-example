package org.xtextcon.xbase.smarthome.jvmmodel

import com.google.inject.Inject
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.xtextcon.xbase.smarthome.rules.Model
import org.eclipse.xtext.xbase.typesystem.IBatchTypeResolver
import org.xtextcon.xbase.smarthome.rules.Rule
import org.xtextcon.xbase.smarthome.rules.Device
import org.xtextcon.xbase.smarthome.rules.State
import org.eclipse.xtext.common.types.JvmGenericType
import java.util.Scanner
import org.eclipse.xtext.common.types.util.TypeReferences
import org.eclipse.xtext.common.types.JvmVisibility
import java.util.Calendar
import org.xtextcon.xbase.smarthome.lib.TimeDependent
import org.xtextcon.xbase.smarthome.lib.Simulator
import java.util.ResourceBundle

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>
 */
class RulesJvmModelInferrer extends AbstractModelInferrer {
	
	/**
	 * Standard service to create JVM types, e.g. classes, fields and methods
	 */
	@Inject extension JvmTypesBuilder
	
	/**
	 * Utility to create type references, e.g. used to produce the signature {@code trigger(Enum<?>)}
	 */
	@Inject extension TypeReferences

	/**
	 * Type inferencer. May only be used to compute the body of a method or the initializer of a field.
	 */
	@Inject IBatchTypeResolver batchTypeResolver
	
	/**
	 * Infers a couple of classes from a model, e.g. enums for the devices and a state machines for the simulator.
	 */
	def dispatch void infer(Model model, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
		val packageName = "my.home.is.my.castle"
		model.declarations.filter(Device).forEach [ device |
			acceptor.accept(device.toEnumerationType(packageName+"."+device.name) [
				for (state : device.states) {
					members += state.toEnumerationLiteral(state.name)
				}
			])
		]
		val rules = model.declarations.filter(Rule)
		if (!rules.empty) {
			val machineName = packageName+"."+model.eResource.URI.trimFileExtension.lastSegment.toFirstUpper+"RuleEngine"
			acceptor.accept(model.toClass(machineName))
				.initializeLater([
					initializeResourceBundle(model, machineName)
					initializeTimeEvents(model, rules.filter[time != null])
					initializeStateEvents(model, rules.filter[when != null])
					initializeRuleEngine(model, rules.filter[when != null], rules.filter[time != null])
					initializeActions(model, rules)
					initializeMain(model)
				])
		}
	}
	
	/**
	 * Creates a field for the generated resource bundle (see {@link SmarthomeGenerator}) and a getter
	 * to obtain the strings from that bundle.
	 */
	def initializeResourceBundle(JvmGenericType type, Model model, String bundleName) {
		type.members += model.toField('RESOURCE_BUNDLE', model.newTypeRef(ResourceBundle)) [
			static = true
			final = true
			initializer = '''
				«ResourceBundle».getBundle("«bundleName»");
			'''
		]
		type.members += model.toMethod('localize', model.newTypeRef(String)) [
			parameters += model.toParameter('key', model.newTypeRef(String))
			visibility = JvmVisibility.PRIVATE
			static = true
			body = '''
				return RESOURCE_BUNDLE.getString(key);
			'''
		]
	}
	
	/**
	 * Produces the logic in the simulator that handles time events, e.g. boolean utility
	 * to compare two instances of {@link Calendar} by the time of the day, and the necessary
	 * trigger code. One could imagine that this utility could also be implemented in a super type
	 * of the simulator.
	 */
	def initializeTimeEvents(JvmGenericType type, Model model, Iterable<? extends Rule> rules) {
		// make this type a TimeDependent type
		if (!rules.isEmpty) {
			type.superTypes += model.newTypeRef(TimeDependent)
		}
		// The dispatcher that'll trigger a certain rule if it matches the given time
		type.members += model.toMethod('trigger', model.newTypeRef(Void.TYPE)) [
			parameters += model.toParameter("time", model.newTypeRef(Calendar))
			body = '''
				«FOR rule : rules»
					if (isTime(time, «rule.timeMethod»())) {
						«System».out.printf(localize("current_time"), time);
						«IF rule.triggersEvent»
							trigger(«rule.thenMethod»());
						«ELSE»
							«rule.thenMethod»();
						«ENDIF»
					}
				«ENDFOR»
			'''
		]
		// The query for equal times
		type.members += model.toMethod('isTime', model.newTypeRef(boolean)) [
			parameters += model.toParameter("c1", model.newTypeRef(Calendar))
			parameters += model.toParameter("c2", model.newTypeRef(Calendar))
			visibility = JvmVisibility.PRIVATE
			body = '''
				return c1.get(«Calendar».HOUR_OF_DAY) == c2.get(«Calendar».HOUR_OF_DAY)
				  && c1.get(«Calendar».MINUTE) == c2.get(«Calendar».MINUTE);
			'''
		]
		// Add the getters for the time when the rule should fire.
		for (rule : rules) {
			type.members += rule.toMethod(rule.timeMethod, rule.time.inferredType) [
				body = rule.time
			]
		}
	}
	
	/**
	 * Add the dispatcher for all state-change events, the dispatcher for simulated user
	 * interaction like {@code Window open}.
	 */
	def initializeStateEvents(JvmGenericType type, Model model, Iterable<? extends Rule> rules) {
		type.members += model.toMethod('trigger', model.newTypeRef(Void.TYPE)) [
			parameters += model.toParameter("event", model.newTypeRef(Enum, wildCardExtends(model.newTypeRef(Object))))
			visibility = JvmVisibility.PROTECTED
			body = '''
				«System».out.printf(localize("received_signal"), event.getClass().getSimpleName(), event);
				«FOR rule : rules»
					if (event == «rule.when.device.name».«rule.when.name») {
						«IF rule.triggersEvent»
							trigger(«rule.thenMethod»());
						«ELSE»
							«rule.thenMethod»();
						«ENDIF»
					}
				«ENDFOR»
			'''
		]
	}
	
	/**
	 * Creates the actions that are triggered by the available rules.
	 */
	def initializeActions(JvmGenericType type, Model model, Iterable<? extends Rule> rules) {
		for (rule : rules.filter[then != null]) {
			type.members += rule.toMethod(rule.thenMethod, rule.then.inferredType) [
				body = rule.then
			]
		}		
	}
	
	/**
	 * Add the {@code run()} method for this state machine. It implements the parsing of the user
	 * input and the dispatching of the simulated events. Thereby it complements the time based events.
	 */
	def initializeRuleEngine(JvmGenericType type, Model model, Iterable<? extends Rule> stateRules, Iterable<? extends Rule> timeRules) {
		type.members += model.toMethod("run", model.newTypeRef(Void.TYPE)) [
			body = '''
				«IF !timeRules.empty»
					«Simulator» simulator = new «Simulator»(localize("set_time"));
					simulator.submit(this);
				«ENDIF»
				«Scanner» sc = new «Scanner»(«System».in);
				«System».out.println(localize("simulator_started"));
				«IF !timeRules.empty»
					«System».out.println(" - Set time HH:mm");
				«ENDIF»
				«FOR state : stateRules.map[when.device].map[states].flatten»
					«System».out.println(" - «state.device.name» «state.name»");
				«ENDFOR»
				«System».out.println(localize("waiting"));
				while(sc.hasNextLine()) {
					«String» command = sc.nextLine();
					«String»[] split = command.split(" ");
					«IF !timeRules.empty»
						if (split.length == 3) {
							String[] time = split[2].split(":");
							simulator.setTime(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
							continue;
						}
					«ENDIF»
					switch(split[0]) {
						«FOR device : stateRules.map[when.device].filterNull»
							case "«device.name»":
								switch(split[1]) {
									«FOR state : device.states»
										case "«state.name»":
											trigger(«device.name».«state.name»);
											break;
									«ENDFOR»
									default:
										«System».err.printf(localize("state_unknown"), split[1], split[0]);
								}
								break;
						«ENDFOR»
						default:
							«System».err.printf(localize("device_unknown"), split[0]);
					}
					«System».out.println(localize("waiting"));
				}
			'''
		]
	}
	
	/**
	 * Create a java main method that will call the {@code run()} function of this class.
	 */
	def initializeMain(JvmGenericType type, Model model) {
		type.members += model.toMethod('main', model.newTypeRef(Void.TYPE)) [
			parameters += model.toParameter("args", model.newTypeRef(String).addArrayTypeDimension)
			static = true
			body = '''
				new «type.simpleName»().run();
			'''
		]
	}
	
	/**
	 * Query the return type of the rule's action. May only be used during the body processing.
	 * That means, it cannot be queried before the model was linked but only at code generation time.
	 */
	def boolean triggersEvent(Rule rule) {
		val types = batchTypeResolver.resolveTypes(rule.then)
		val returnType = types.getReturnType(rule.then)
		if (returnType.isSubtypeOf(Enum)) {
			return true;
		}
		return false;
	}
	
	def getThenMethod(Rule rule) {
		'then_'+rule.eContainer.eContents.indexOf(rule)
	}
	
	def getTimeMethod(Rule rule) {
		'time_'+rule.eContainer.eContents.indexOf(rule)
	}
	
	def getDevice(State state) {
		state.eContainer as Device
	}
}

