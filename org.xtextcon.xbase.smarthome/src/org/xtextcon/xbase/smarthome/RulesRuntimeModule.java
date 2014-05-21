/*
 * generated by Xtext
 */
package org.xtextcon.xbase.smarthome;

import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputer;
import org.xtextcon.xbase.smarthome.jvmmodel.SmarthomeCompiler;
import org.xtextcon.xbase.smarthome.typesystem.SmarthomeTypeComputer;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class RulesRuntimeModule extends org.xtextcon.xbase.smarthome.AbstractRulesRuntimeModule {

	@Override
	public Class<? extends ITypeComputer> bindITypeComputer() {
		return SmarthomeTypeComputer.class;
	}
	
	public Class<? extends XbaseCompiler> bindCompiler() {
		return SmarthomeCompiler.class;
	}
	
}
