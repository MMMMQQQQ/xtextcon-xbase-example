package org.xtextcon.xbase.smarthome.typesystem

import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState
import org.xtextcon.xbase.smarthome.rules.TimeLiteral
import java.util.Calendar

class SmarthomeTypeComputer extends XbaseTypeComputer {
	
	override computeTypes(XExpression expression, ITypeComputationState state) {
		if (expression instanceof TimeLiteral)
			expression.computeType(state)
		else
			super.computeTypes(expression, state)
	}
	
	def void computeType(TimeLiteral literal, ITypeComputationState state) {
		state.acceptActualType(getRawTypeForName(Calendar, state.referenceOwner))
	}
	
}