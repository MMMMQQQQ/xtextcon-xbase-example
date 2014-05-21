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
import java.text.SimpleDateFormat
import org.xtextcon.xbase.smarthome.lib.TimeDependent
import org.xtextcon.xbase.smarthome.lib.Simulator

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>
 */
class RulesJvmModelInferrer extends AbstractModelInferrer {
	
	@Inject extension JvmTypesBuilder
	
	@Inject extension TypeReferences

	@Inject IBatchTypeResolver batchTypeResolver
	
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
					initializeTimeEvents(model, rules.filter[time != null])
					initializeStateEvents(model, rules.filter[when != null])
					initializeRuleEngine(model, rules.filter[when != null], rules.filter[time != null])
					initializeActions(model, rules)
					initializeMain(model)
				])
		}
	}
	
	def initializeTimeEvents(JvmGenericType type, Model model, Iterable<? extends Rule> rules) {
		if (!rules.isEmpty) {
			type.superTypes += model.newTypeRef(TimeDependent)
		}
		type.members += model.toMethod('trigger', model.newTypeRef(Void.TYPE)) [
			parameters += model.toParameter("time", model.newTypeRef(Calendar))
			body = '''
				«FOR rule : rules»
					if (isTime(time, «rule.timeMethod»())) {
						«System».out.println("Current time '"+new «SimpleDateFormat»("HH:mm").format(time.getTime()) + "'.");
						«IF rule.triggersEvent»
							trigger(«rule.thenMethod»());
						«ELSE»
							«rule.thenMethod»();
						«ENDIF»
					}
				«ENDFOR»
			'''
		]
		type.members += model.toMethod('isTime', model.newTypeRef(boolean)) [
			parameters += model.toParameter("c1", model.newTypeRef(Calendar))
			parameters += model.toParameter("c2", model.newTypeRef(Calendar))
			visibility = JvmVisibility.PRIVATE
			body = '''
				return c1.get(«Calendar».HOUR_OF_DAY) == c2.get(«Calendar».HOUR_OF_DAY)
				  && c1.get(«Calendar».MINUTE) == c2.get(«Calendar».MINUTE);
			'''
		]
		
		for (rule : rules) {
			type.members += rule.toMethod(rule.timeMethod, rule.time.inferredType) [
				body = rule.time
			]
		}
	}
	
	def initializeStateEvents(JvmGenericType type, Model model, Iterable<? extends Rule> rules) {
		type.members += model.toMethod('trigger', model.newTypeRef(Void.TYPE)) [
			parameters += model.toParameter("event", model.newTypeRef(Enum, wildCardExtends(model.newTypeRef(Object))))
			visibility = JvmVisibility.PROTECTED
			body = '''
				«System».out.println("Received signal '"+event.getClass().getSimpleName()+" "+event+"'.");
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
	
	def initializeActions(JvmGenericType type, Model model, Iterable<? extends Rule> rules) {
		for (rule : rules.filter[then != null]) {
			type.members += rule.toMethod(rule.thenMethod, rule.then.inferredType) [
				body = rule.then
			]
		}		
	}
	
	def initializeRuleEngine(JvmGenericType type, Model model, Iterable<? extends Rule> stateRules, Iterable<? extends Rule> timeRules) {
		type.members += model.toMethod("run", model.newTypeRef(Void.TYPE)) [
			body = '''
				«IF !timeRules.empty»
					«Simulator» simulator = new «Simulator»();
					simulator.submit(this);
				«ENDIF»
				«Scanner» sc = new «Scanner»(«System».in);
				«System».out.println("Simulator started. These commands are available: ");
				«IF !timeRules.empty»
					«System».out.println(" - Set time HH:mm");
				«ENDIF»
				«FOR state : stateRules.map[when.device].map[states].flatten»
					«System».out.println(" - «state.device.name» «state.name»");
				«ENDFOR»
				«System».out.println("Waiting for input...");
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
										«System».err.println("The state "+split[1]+" is not defined for device "+split[0]+".");
								}
								break;
						«ENDFOR»
						default:
							«System».err.println("Unknown device "+split[0]+ ".");
					}
					«System».out.println("Waiting for input...");
				}
			'''
		]
	}
	
	def initializeMain(JvmGenericType type, Model model) {
		type.members += model.toMethod('main', model.newTypeRef(Void.TYPE)) [
			parameters += model.toParameter("args", model.newTypeRef(String).addArrayTypeDimension)
			static = true
			body = '''
				new «type.simpleName»().run();
			'''
		]
	}
	
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

