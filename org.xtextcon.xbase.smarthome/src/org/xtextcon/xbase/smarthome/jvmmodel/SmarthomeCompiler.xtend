package org.xtextcon.xbase.smarthome.jvmmodel

import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.xtextcon.xbase.smarthome.rules.TimeLiteral
import java.util.Calendar

class SmarthomeCompiler extends XbaseCompiler {
	
	override protected canCompileToJavaExpression(XExpression expression, ITreeAppendable appendable) {
		if (expression instanceof TimeLiteral)
			return false
		return super.canCompileToJavaExpression(expression, appendable)
	}
	
	override internalToConvertedExpression(XExpression obj, ITreeAppendable appendable) {
		if (obj instanceof TimeLiteral)
			obj.toJavaExpression(appendable)
		else
			super.internalToConvertedExpression(obj, appendable)
	}
	
	def toJavaExpression(TimeLiteral literal, ITreeAppendable appendable) {
		appendable.trace(literal, false).append(getVarName(literal, appendable));
	}
	
	override doInternalToJavaStatement(XExpression obj, ITreeAppendable appendable, boolean isReferenced) {
		if (obj instanceof TimeLiteral)
			obj.toJavaStatement(appendable, isReferenced)
		else
			super.doInternalToJavaStatement(obj, appendable, isReferenced)
	}
	
	def toJavaStatement(TimeLiteral literal, ITreeAppendable appendable, boolean isReferenced) {
		val myAppendable = appendable.newLine.trace(literal)
		val variableName = myAppendable.declareSyntheticVariable(literal, '_calendar')
		myAppendable
			.append('final ')
			.append(Calendar)
			.append(''' «variableName» = ''')
			.append(Calendar)
			.append('.getInstance();')
			.newLine
			.append('''«variableName».set(0, 0, 0, «literal.hour», «literal.min», «literal.sec»);''')
	}
}