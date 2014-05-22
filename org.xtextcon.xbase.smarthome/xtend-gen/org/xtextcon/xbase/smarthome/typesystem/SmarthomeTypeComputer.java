package org.xtextcon.xbase.smarthome.typesystem;

import java.util.Arrays;
import java.util.Calendar;
import org.eclipse.xtext.xbase.XAbstractFeatureCall;
import org.eclipse.xtext.xbase.XAssignment;
import org.eclipse.xtext.xbase.XBasicForLoopExpression;
import org.eclipse.xtext.xbase.XBlockExpression;
import org.eclipse.xtext.xbase.XBooleanLiteral;
import org.eclipse.xtext.xbase.XCastedExpression;
import org.eclipse.xtext.xbase.XClosure;
import org.eclipse.xtext.xbase.XConstructorCall;
import org.eclipse.xtext.xbase.XDoWhileExpression;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XForLoopExpression;
import org.eclipse.xtext.xbase.XIfExpression;
import org.eclipse.xtext.xbase.XInstanceOfExpression;
import org.eclipse.xtext.xbase.XListLiteral;
import org.eclipse.xtext.xbase.XNullLiteral;
import org.eclipse.xtext.xbase.XNumberLiteral;
import org.eclipse.xtext.xbase.XReturnExpression;
import org.eclipse.xtext.xbase.XSetLiteral;
import org.eclipse.xtext.xbase.XStringLiteral;
import org.eclipse.xtext.xbase.XSwitchExpression;
import org.eclipse.xtext.xbase.XSynchronizedExpression;
import org.eclipse.xtext.xbase.XThrowExpression;
import org.eclipse.xtext.xbase.XTryCatchFinallyExpression;
import org.eclipse.xtext.xbase.XTypeLiteral;
import org.eclipse.xtext.xbase.XVariableDeclaration;
import org.eclipse.xtext.xbase.XWhileExpression;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState;
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer;
import org.eclipse.xtext.xbase.typesystem.references.ITypeReferenceOwner;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;
import org.xtextcon.xbase.smarthome.rules.TimeLiteral;

@SuppressWarnings("all")
public class SmarthomeTypeComputer extends XbaseTypeComputer {
  protected void _computeTypes(final TimeLiteral literal, final ITypeComputationState state) {
    ITypeReferenceOwner _referenceOwner = state.getReferenceOwner();
    LightweightTypeReference _rawTypeForName = this.getRawTypeForName(Calendar.class, _referenceOwner);
    state.acceptActualType(_rawTypeForName);
  }
  
  public void computeTypes(final XExpression literal, final ITypeComputationState state) {
    if (literal instanceof XAssignment) {
      _computeTypes((XAssignment)literal, state);
      return;
    } else if (literal instanceof XDoWhileExpression) {
      _computeTypes((XDoWhileExpression)literal, state);
      return;
    } else if (literal instanceof XListLiteral) {
      _computeTypes((XListLiteral)literal, state);
      return;
    } else if (literal instanceof XSetLiteral) {
      _computeTypes((XSetLiteral)literal, state);
      return;
    } else if (literal instanceof XWhileExpression) {
      _computeTypes((XWhileExpression)literal, state);
      return;
    } else if (literal instanceof XAbstractFeatureCall) {
      _computeTypes((XAbstractFeatureCall)literal, state);
      return;
    } else if (literal instanceof XBasicForLoopExpression) {
      _computeTypes((XBasicForLoopExpression)literal, state);
      return;
    } else if (literal instanceof XBlockExpression) {
      _computeTypes((XBlockExpression)literal, state);
      return;
    } else if (literal instanceof XBooleanLiteral) {
      _computeTypes((XBooleanLiteral)literal, state);
      return;
    } else if (literal instanceof XCastedExpression) {
      _computeTypes((XCastedExpression)literal, state);
      return;
    } else if (literal instanceof XClosure) {
      _computeTypes((XClosure)literal, state);
      return;
    } else if (literal instanceof XConstructorCall) {
      _computeTypes((XConstructorCall)literal, state);
      return;
    } else if (literal instanceof XForLoopExpression) {
      _computeTypes((XForLoopExpression)literal, state);
      return;
    } else if (literal instanceof XIfExpression) {
      _computeTypes((XIfExpression)literal, state);
      return;
    } else if (literal instanceof XInstanceOfExpression) {
      _computeTypes((XInstanceOfExpression)literal, state);
      return;
    } else if (literal instanceof XNullLiteral) {
      _computeTypes((XNullLiteral)literal, state);
      return;
    } else if (literal instanceof XNumberLiteral) {
      _computeTypes((XNumberLiteral)literal, state);
      return;
    } else if (literal instanceof XReturnExpression) {
      _computeTypes((XReturnExpression)literal, state);
      return;
    } else if (literal instanceof XStringLiteral) {
      _computeTypes((XStringLiteral)literal, state);
      return;
    } else if (literal instanceof XSwitchExpression) {
      _computeTypes((XSwitchExpression)literal, state);
      return;
    } else if (literal instanceof XSynchronizedExpression) {
      _computeTypes((XSynchronizedExpression)literal, state);
      return;
    } else if (literal instanceof XThrowExpression) {
      _computeTypes((XThrowExpression)literal, state);
      return;
    } else if (literal instanceof XTryCatchFinallyExpression) {
      _computeTypes((XTryCatchFinallyExpression)literal, state);
      return;
    } else if (literal instanceof XTypeLiteral) {
      _computeTypes((XTypeLiteral)literal, state);
      return;
    } else if (literal instanceof XVariableDeclaration) {
      _computeTypes((XVariableDeclaration)literal, state);
      return;
    } else if (literal instanceof TimeLiteral) {
      _computeTypes((TimeLiteral)literal, state);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(literal, state).toString());
    }
  }
}
