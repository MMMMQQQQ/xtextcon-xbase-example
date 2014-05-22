package org.xtextcon.xbase.smarthome.typesystem

import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState
import org.xtextcon.xbase.smarthome.rules.TimeLiteral
import java.util.Calendar

class SmarthomeTypeComputer extends XbaseTypeComputer {
	
	def dispatch void computeTypes(TimeLiteral literal, ITypeComputationState state) {
		state.acceptActualType(getRawTypeForName(Calendar, state.referenceOwner))
	}
	
}