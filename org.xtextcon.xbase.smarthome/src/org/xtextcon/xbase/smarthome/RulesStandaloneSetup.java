/*
* generated by Xtext
*/
package org.xtextcon.xbase.smarthome;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class RulesStandaloneSetup extends RulesStandaloneSetupGenerated{

	public static void doSetup() {
		new RulesStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

