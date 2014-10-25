package org.xtextcon.xbase.smarthome.generator

import org.eclipse.xtext.xbase.compiler.JvmModelGenerator
import org.xtextcon.xbase.smarthome.rules.Model
import org.eclipse.xtext.generator.IFileSystemAccess

class SmarthomeGenerator extends JvmModelGenerator {
	
	/**
	 * Generates a properties with the messages that are necessary to run the simulator.
	 */
	def dispatch void internalDoGenerate(Model model, IFileSystemAccess fsa) {
		val resourceName = "my/home/is/my/castle/" + model.eResource.URI.trimFileExtension.lastSegment.toFirstUpper+"RuleEngine.properties"
		fsa.generateFile(resourceName, '''
			current_time = Current time '%tR'.\n
			received_signal = Received signal '%s %s'.\n
			simulator_started = Simulator started. These commands are available:
			waiting = Waiting for input...
			state_unknown = The state %s is not defined for device %s.\n
			device_unknown = Unknown device %s.\n
			set_time = Time set to %tR\n
		''')
	}
	
}