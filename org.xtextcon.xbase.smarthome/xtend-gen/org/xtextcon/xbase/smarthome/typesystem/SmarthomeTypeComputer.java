package org.xtextcon.xbase.smarthome.typesystem;

import java.util.Calendar;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState;
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer;
import org.eclipse.xtext.xbase.typesystem.references.ITypeReferenceOwner;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;
import org.xtextcon.xbase.smarthome.rules.TimeLiteral;

@SuppressWarnings("all")
public class SmarthomeTypeComputer extends XbaseTypeComputer {
  public void computeTypes(final XExpression expression, final ITypeComputationState state) {
    if ((expression instanceof TimeLiteral)) {
      this.computeType(((TimeLiteral)expression), state);
    } else {
      super.computeTypes(expression, state);
    }
  }
  
  public void computeType(final TimeLiteral literal, final ITypeComputationState state) {
    ITypeReferenceOwner _referenceOwner = state.getReferenceOwner();
    LightweightTypeReference _rawTypeForName = this.getRawTypeForName(Calendar.class, _referenceOwner);
    state.acceptActualType(_rawTypeForName);
  }
}
