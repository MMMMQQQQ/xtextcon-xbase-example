/**
 */
package org.xtextcon.xbase.smarthome.rules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.xtextcon.xbase.smarthome.rules.RulesPackage
 * @generated
 */
public interface RulesFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  RulesFactory eINSTANCE = org.xtextcon.xbase.smarthome.rules.impl.RulesFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Model</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Model</em>'.
   * @generated
   */
  Model createModel();

  /**
   * Returns a new object of class '<em>Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Declaration</em>'.
   * @generated
   */
  Declaration createDeclaration();

  /**
   * Returns a new object of class '<em>Rule</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Rule</em>'.
   * @generated
   */
  Rule createRule();

  /**
   * Returns a new object of class '<em>Device</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Device</em>'.
   * @generated
   */
  Device createDevice();

  /**
   * Returns a new object of class '<em>State</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>State</em>'.
   * @generated
   */
  State createState();

  /**
   * Returns a new object of class '<em>Time Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Time Literal</em>'.
   * @generated
   */
  TimeLiteral createTimeLiteral();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  RulesPackage getRulesPackage();

} //RulesFactory
