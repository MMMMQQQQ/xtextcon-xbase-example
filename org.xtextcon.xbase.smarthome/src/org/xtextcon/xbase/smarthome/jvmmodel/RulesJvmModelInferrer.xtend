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
					initializeRuleEngine(model, rules)
				])
		}
		
	}
	
	def initializeRuleEngine(JvmGenericType type, Model model, Iterable<? extends Rule> rules) {
		type.members += model.toMethod('main', model.newTypeRef(Void.TYPE)) [
			parameters += model.toParameter("args", model.newTypeRef(String).addArrayTypeDimension)
			static = true
			body = '''
				new «type.simpleName»().run();
			'''
		]
		
		type.members += model.toMethod("run", model.newTypeRef(Void.TYPE)) [
			body = '''
				«Scanner» sc = new «Scanner»(«System».in);
				«System».out.println("Simulator started. These commands are available: ");
				«FOR state : rules.map[when.device].map[states].flatten»
					«System».out.println(" - «state.device.name» «state.name»");
				«ENDFOR»
				«System».out.println("Waiting for input...");
				while(sc.hasNextLine()) {
					«String» command = sc.nextLine();
					«String»[] split = command.split(" ");
					switch(split[0]) {
						«FOR device : rules.map[when.device]»
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
		
		for (rule : rules.filter[then!=null]) {
			type.members += rule.toMethod(rule.thenMethod, rule.then.inferredType) [
				body = rule.then
			]
		}
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
	
	def getDevice(State state) {
		state.eContainer as Device
	}
}

