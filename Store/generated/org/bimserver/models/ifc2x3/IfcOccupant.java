/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bimserver.models.ifc2x3;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ifc Occupant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bimserver.models.ifc2x3.IfcOccupant#getPredefinedType <em>Predefined Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bimserver.models.ifc2x3.Ifc2x3Package#getIfcOccupant()
 * @model
 * @generated
 */
public interface IfcOccupant extends IfcActor {
	/**
	 * Returns the value of the '<em><b>Predefined Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.bimserver.models.ifc2x3.IfcOccupantTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Predefined Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Predefined Type</em>' attribute.
	 * @see org.bimserver.models.ifc2x3.IfcOccupantTypeEnum
	 * @see #setPredefinedType(IfcOccupantTypeEnum)
	 * @see org.bimserver.models.ifc2x3.Ifc2x3Package#getIfcOccupant_PredefinedType()
	 * @model
	 * @generated
	 */
	IfcOccupantTypeEnum getPredefinedType();

	/**
	 * Sets the value of the '{@link org.bimserver.models.ifc2x3.IfcOccupant#getPredefinedType <em>Predefined Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Predefined Type</em>' attribute.
	 * @see org.bimserver.models.ifc2x3.IfcOccupantTypeEnum
	 * @see #getPredefinedType()
	 * @generated
	 */
	void setPredefinedType(IfcOccupantTypeEnum value);

} // IfcOccupant