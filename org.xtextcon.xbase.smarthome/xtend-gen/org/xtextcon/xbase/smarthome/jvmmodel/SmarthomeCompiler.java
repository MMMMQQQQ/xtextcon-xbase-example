package org.xtextcon.xbase.smarthome.jvmmodel;

import java.util.Calendar;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;
import org.xtextcon.xbase.smarthome.rules.TimeLiteral;

@SuppressWarnings("all")
public class SmarthomeCompiler extends XbaseCompiler {
  /**
   * Make sure that the compiler provides a proper statement context when a time literal
   * should be compiled in an expression context.
   */
  protected boolean canCompileToJavaExpression(final XExpression expression, final ITreeAppendable appendable) {
    if ((expression instanceof TimeLiteral)) {
      return false;
    }
    return super.canCompileToJavaExpression(expression, appendable);
  }
  
  /**
   * Dispatch according to the concrete expression type.
   * This is used if the expression should be compiled in an expression context, e.g.
   * in a parameter list.
   */
  public void internalToConvertedExpression(final XExpression obj, final ITreeAppendable appendable) {
    if ((obj instanceof TimeLiteral)) {
      this.toJavaExpression(((TimeLiteral)obj), appendable);
    } else {
      super.internalToConvertedExpression(obj, appendable);
    }
  }
  
  public ITreeAppendable toJavaExpression(final TimeLiteral literal, final ITreeAppendable appendable) {
    ITreeAppendable _trace = appendable.trace(literal, false);
    String _varName = this.getVarName(literal, appendable);
    return _trace.append(_varName);
  }
  
  /**
   * Dispatch according to the concrete expression type.
   * This is used if the expression should be compiled in a statement context.
   */
  public void doInternalToJavaStatement(final XExpression obj, final ITreeAppendable appendable, final boolean isReferenced) {
    if ((obj instanceof TimeLiteral)) {
      this.toJavaStatement(((TimeLiteral)obj), appendable, isReferenced);
    } else {
      super.doInternalToJavaStatement(obj, appendable, isReferenced);
    }
  }
  
  public ITreeAppendable toJavaStatement(final TimeLiteral literal, final ITreeAppendable appendable, final boolean isReferenced) {
    ITreeAppendable _xblockexpression = null;
    {
      ITreeAppendable _newLine = appendable.newLine();
      final ITreeAppendable myAppendable = _newLine.trace(literal);
      final String variableName = myAppendable.declareSyntheticVariable(literal, "_calendar");
      ITreeAppendable _append = myAppendable.append("final ");
      ITreeAppendable _append_1 = _append.append(Calendar.class);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(" ");
      _builder.append(variableName, " ");
      _builder.append(" = ");
      ITreeAppendable _append_2 = _append_1.append(_builder);
      ITreeAppendable _append_3 = _append_2.append(Calendar.class);
      ITreeAppendable _append_4 = _append_3.append(".getInstance();");
      ITreeAppendable _newLine_1 = _append_4.newLine();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(variableName, "");
      _builder_1.append(".set(0, 0, 0, ");
      int _hour = literal.getHour();
      _builder_1.append(_hour, "");
      _builder_1.append(", ");
      int _min = literal.getMin();
      _builder_1.append(_min, "");
      _builder_1.append(", ");
      int _sec = literal.getSec();
      _builder_1.append(_sec, "");
      _builder_1.append(");");
      _xblockexpression = _newLine_1.append(_builder_1);
    }
    return _xblockexpression;
  }
}
