/**
 */
package org.xtextcon.xbase.smarthome.rules;

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.TimeLiteral#getHour <em>Hour</em>}</li>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.TimeLiteral#getMin <em>Min</em>}</li>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.TimeLiteral#getSec <em>Sec</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getTimeLiteral()
 * @model
 * @generated
 */
public interface TimeLiteral extends XExpression
{
  /**
   * Returns the value of the '<em><b>Hour</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hour</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hour</em>' attribute.
   * @see #setHour(int)
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getTimeLiteral_Hour()
   * @model
   * @generated
   */
  int getHour();

  /**
   * Sets the value of the '{@link org.xtextcon.xbase.smarthome.rules.TimeLiteral#getHour <em>Hour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hour</em>' attribute.
   * @see #getHour()
   * @generated
   */
  void setHour(int value);

  /**
   * Returns the value of the '<em><b>Min</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Min</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Min</em>' attribute.
   * @see #setMin(int)
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getTimeLiteral_Min()
   * @model
   * @generated
   */
  int getMin();

  /**
   * Sets the value of the '{@link org.xtextcon.xbase.smarthome.rules.TimeLiteral#getMin <em>Min</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Min</em>' attribute.
   * @see #getMin()
   * @generated
   */
  void setMin(int value);

  /**
   * Returns the value of the '<em><b>Sec</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sec</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sec</em>' attribute.
   * @see #setSec(int)
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getTimeLiteral_Sec()
   * @model
   * @generated
   */
  int getSec();

  /**
   * Sets the value of the '{@link org.xtextcon.xbase.smarthome.rules.TimeLiteral#getSec <em>Sec</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sec</em>' attribute.
   * @see #getSec()
   * @generated
   */
  void setSec(int value);

} // TimeLiteral
