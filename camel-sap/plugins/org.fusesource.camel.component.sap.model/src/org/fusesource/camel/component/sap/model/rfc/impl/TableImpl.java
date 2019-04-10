/**
 * Copyright 2013 Red Hat, Inc.
 * 
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 */
package org.fusesource.camel.component.sap.model.rfc.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.fusesource.camel.component.sap.model.rfc.RfcPackage;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.fusesource.camel.component.sap.model.rfc.Table;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Table</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.fusesource.camel.component.sap.model.rfc.impl.TableImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.fusesource.camel.component.sap.model.rfc.impl.TableImpl#getLineType <em>Line Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TableImpl<S extends Structure> extends EObjectImpl implements
		Table<S> {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The default value of the '{@link #getLineType() <em>Line Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineType()
	 * @generated
	 * @ordered
	 */
	protected static final String LINE_TYPE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RfcPackage.Literals.TABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		String name = null;
		EAnnotation idocAnnotation = eClass().getEAnnotation(RfcPackage.eNS_URI);
		if (idocAnnotation != null) {
			name = idocAnnotation.getDetails().get("name");
		}
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT 
	 */
	public void setName(String newName) {
		// NOOP
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getLineType() {
		String lineType = null;
		EAnnotation idocAnnotation = eClass().getEAnnotation(RfcPackage.eNS_URI);
		if (idocAnnotation != null) {
			lineType = idocAnnotation.getDetails().get("lineType");
		}
		return lineType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setLineType(String newLineType) {
		// NOOP
	}

	/**
	 * @generated NOT 
	 */
	@SuppressWarnings("unchecked")
	public EList<S> getRows() {
		EStructuralFeature feature = eClass().getEStructuralFeature("row");
		Object value = eGet(feature);
		if (value == null) {
			EClass eClass = ((EReference) feature).getEReferenceType();
			value = eClass.getEPackage().getEFactoryInstance().create(eClass);
			eSet(feature, value);
		}
		return (EList<S>) value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public S getRow(int index) {
		return (S) get(index);
	}

	@Override
	public int size() {
		return getRows().size();
	}

	@Override
	public boolean isEmpty() {
		return getRows().isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return getRows().contains(0);
	}

	@Override
	public Iterator<S> iterator() {
		return getRows().iterator();
	}

	@Override
	public Object[] toArray() {
		return getRows().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return getRows().toArray(a);
	}

	public S add() {
		S newRow = createNewRow();
		getRows().add(newRow);
		return newRow;
	}

	public S add(int index) {
		S newRow = createNewRow();
		getRows().add(index, newRow);
		return newRow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RfcPackage.TABLE__NAME:
				return getName();
			case RfcPackage.TABLE__LINE_TYPE:
				return getLineType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RfcPackage.TABLE__NAME:
				setName((String)newValue);
				return;
			case RfcPackage.TABLE__LINE_TYPE:
				setLineType((String)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case RfcPackage.TABLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RfcPackage.TABLE__LINE_TYPE:
				setLineType(LINE_TYPE_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RfcPackage.TABLE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case RfcPackage.TABLE__LINE_TYPE:
				return LINE_TYPE_EDEFAULT == null ? getLineType() != null : !LINE_TYPE_EDEFAULT.equals(getLineType());
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean add(S e) {
		return getRows().add(e);
	}

	@Override
	public boolean remove(Object o) {
		return getRows().remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return getRows().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends S> c) {
		return getRows().addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends S> c) {
		return getRows().addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return getRows().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return getRows().retainAll(c);
	}

	@Override
	public void clear() {
		getRows().clear();
	}

	@Override
	public S get(int index) {
		return getRows().get(index);
	}

	@Override
	public S set(int index, S element) {
		return getRows().set(index, element);
	}

	@Override
	public void add(int index, S element) {
		getRows().add(index, element);
	}

	@Override
	public S remove(int index) {
		return getRows().remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return getRows().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return getRows().lastIndexOf(o);
	}

	@Override
	public ListIterator<S> listIterator() {
		return getRows().listIterator();
	}

	@Override
	public ListIterator<S> listIterator(int index) {
		return getRows().listIterator(index);
	}

	@Override
	public List<S> subList(int fromIndex, int toIndex) {
		return getRows().subList(fromIndex, toIndex);
	}

	@SuppressWarnings("unchecked")
	protected S createNewRow() {
		EStructuralFeature feature = eClass().getEStructuralFeature("row");
		if (feature == null || !(feature instanceof EReference)) {
			return null;
		}

		EClass rowType = ((EReference) feature).getEReferenceType();

		return (S) rowType.getEPackage().getEFactoryInstance().create(rowType);
	}

} // TableImpl
