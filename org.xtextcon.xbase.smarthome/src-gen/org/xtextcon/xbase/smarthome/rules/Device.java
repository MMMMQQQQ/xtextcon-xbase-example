/**
 */
package org.xtextcon.xbase.smarthome.rules;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Device</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.Device#getName <em>Name</em>}</li>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.Device#getStates <em>States</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getDevice()
 * @model
 * @generated
 */
public interface Device extends Declaration
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getDevice_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.xtextcon.xbase.smarthome.rules.Device#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>States</b></em>' containment reference list.
   * The list contents are of type {@link org.xtextcon.xbase.smarthome.rules.State}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>States</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>States</em>' containment reference list.
   * @see org.xtextcon.xbase.smarthome.rules.RulesPackage#getDevice_States()
   * @model containment="true"
   * @generated
   */
  EList<State> getStates();

} // Device
