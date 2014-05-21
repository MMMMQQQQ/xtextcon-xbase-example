/**
 */
package org.xtextcon.xbase.smarthome.rules.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.xtext.xbase.impl.XExpressionImpl;

import org.xtextcon.xbase.smarthome.rules.RulesPackage;
import org.xtextcon.xbase.smarthome.rules.TimeLiteral;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.impl.TimeLiteralImpl#getHour <em>Hour</em>}</li>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.impl.TimeLiteralImpl#getMin <em>Min</em>}</li>
 *   <li>{@link org.xtextcon.xbase.smarthome.rules.impl.TimeLiteralImpl#getSec <em>Sec</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TimeLiteralImpl extends XExpressionImpl implements TimeLiteral
{
  /**
   * The default value of the '{@link #getHour() <em>Hour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHour()
   * @generated
   * @ordered
   */
  protected static final int HOUR_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getHour() <em>Hour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHour()
   * @generated
   * @ordered
   */
  protected int hour = HOUR_EDEFAULT;

  /**
   * The default value of the '{@link #getMin() <em>Min</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMin()
   * @generated
   * @ordered
   */
  protected static final int MIN_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getMin() <em>Min</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMin()
   * @generated
   * @ordered
   */
  protected int min = MIN_EDEFAULT;

  /**
   * The default value of the '{@link #getSec() <em>Sec</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSec()
   * @generated
   * @ordered
   */
  protected static final int SEC_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getSec() <em>Sec</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSec()
   * @generated
   * @ordered
   */
  protected int sec = SEC_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TimeLiteralImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return RulesPackage.Literals.TIME_LITERAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getHour()
  {
    return hour;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHour(int newHour)
  {
    int oldHour = hour;
    hour = newHour;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.TIME_LITERAL__HOUR, oldHour, hour));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getMin()
  {
    return min;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMin(int newMin)
  {
    int oldMin = min;
    min = newMin;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.TIME_LITERAL__MIN, oldMin, min));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getSec()
  {
    return sec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSec(int newSec)
  {
    int oldSec = sec;
    sec = newSec;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.TIME_LITERAL__SEC, oldSec, sec));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case RulesPackage.TIME_LITERAL__HOUR:
        return getHour();
      case RulesPackage.TIME_LITERAL__MIN:
        return getMin();
      case RulesPackage.TIME_LITERAL__SEC:
        return getSec();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case RulesPackage.TIME_LITERAL__HOUR:
        setHour((Integer)newValue);
        return;
      case RulesPackage.TIME_LITERAL__MIN:
        setMin((Integer)newValue);
        return;
      case RulesPackage.TIME_LITERAL__SEC:
        setSec((Integer)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case RulesPackage.TIME_LITERAL__HOUR:
        setHour(HOUR_EDEFAULT);
        return;
      case RulesPackage.TIME_LITERAL__MIN:
        setMin(MIN_EDEFAULT);
        return;
      case RulesPackage.TIME_LITERAL__SEC:
        setSec(SEC_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case RulesPackage.TIME_LITERAL__HOUR:
        return hour != HOUR_EDEFAULT;
      case RulesPackage.TIME_LITERAL__MIN:
        return min != MIN_EDEFAULT;
      case RulesPackage.TIME_LITERAL__SEC:
        return sec != SEC_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (hour: ");
    result.append(hour);
    result.append(", min: ");
    result.append(min);
    result.append(", sec: ");
    result.append(sec);
    result.append(')');
    return result.toString();
  }

} //TimeLiteralImpl
