/**
 */
package org.xtextcon.xbase.smarthome.rules;

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.Rule#getWhen <em>When</em>}</li>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.Rule#getTime <em>Time</em>}</li>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.Rule#getThen <em>Then</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getRule()
 * @model
 * @generated
 */
public interface Rule extends Declaration
{
  /**
   * Returns the value of the '<em><b>When</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>When</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>When</em>' reference.
   * @see #setWhen(State)
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getRule_When()
   * @model
   * @generated
   */
  State getWhen();

  /**
   * Sets the value of the '{@link org.xtextcon.xbase.smarthome.rules.Rule#getWhen <em>When</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>When</em>' reference.
   * @see #getWhen()
   * @generated
   */
  void setWhen(State value);

  /**
   * Returns the value of the '<em><b>Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Time</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Time</em>' containment reference.
   * @see #setTime(TimeLiteral)
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getRule_Time()
   * @model containment="true"
   * @generated
   */
  TimeLiteral getTime();

  /**
   * Sets the value of the '{@link org.xtextcon.xbase.smarthome.rules.Rule#getTime <em>Time</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Time</em>' containment reference.
   * @see #getTime()
   * @generated
   */
  void setTime(TimeLiteral value);

  /**
   * Returns the value of the '<em><b>Then</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Then</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Then</em>' containment reference.
   * @see #setThen(XExpression)
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getRule_Then()
   * @model containment="true"
   * @generated
   */
  XExpression getThen();

  /**
   * Sets the value of the '{@link org.xtextcon.xbase.smarthome.rules.Rule#getThen <em>Then</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Then</em>' containment reference.
   * @see #getThen()
   * @generated
   */
  void setThen(XExpression value);

} // Rule
