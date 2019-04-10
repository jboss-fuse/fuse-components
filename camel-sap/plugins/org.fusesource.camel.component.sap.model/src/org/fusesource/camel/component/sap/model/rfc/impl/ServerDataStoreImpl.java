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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.fusesource.camel.component.sap.model.rfc.RfcPackage;
import org.fusesource.camel.component.sap.model.rfc.ServerData;
import org.fusesource.camel.component.sap.model.rfc.ServerDataStore;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Server Data Store</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.fusesource.camel.component.sap.model.rfc.impl.ServerDataStoreImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link org.fusesource.camel.component.sap.model.rfc.impl.ServerDataStoreImpl#getServerData <em>Server Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerDataStoreImpl extends EObjectImpl implements ServerDataStore {
	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, ServerData> entries;

	/**
	 * The cached value of the '{@link #getServerData() <em>Server Data</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServerData()
	 * @generated
	 * @ordered
	 */
	protected EList<ServerData> serverData;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServerDataStoreImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RfcPackage.Literals.SERVER_DATA_STORE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, ServerData> getEntries() {
		if (entries == null) {
			entries = new EcoreEMap<String,ServerData>(RfcPackage.Literals.SERVER_DATA_STORE_ENTRY, ServerDataStoreEntryImpl.class, this, RfcPackage.SERVER_DATA_STORE__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ServerData> getServerData() {
		if (serverData == null) {
			serverData = new EObjectContainmentEList<ServerData>(ServerData.class, this, RfcPackage.SERVER_DATA_STORE__SERVER_DATA);
		}
		return serverData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RfcPackage.SERVER_DATA_STORE__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
			case RfcPackage.SERVER_DATA_STORE__SERVER_DATA:
				return ((InternalEList<?>)getServerData()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RfcPackage.SERVER_DATA_STORE__ENTRIES:
				if (coreType) return getEntries();
				else return getEntries().map();
			case RfcPackage.SERVER_DATA_STORE__SERVER_DATA:
				return getServerData();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RfcPackage.SERVER_DATA_STORE__ENTRIES:
				((EStructuralFeature.Setting)getEntries()).set(newValue);
				return;
			case RfcPackage.SERVER_DATA_STORE__SERVER_DATA:
				getServerData().clear();
				getServerData().addAll((Collection<? extends ServerData>)newValue);
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
			case RfcPackage.SERVER_DATA_STORE__ENTRIES:
				getEntries().clear();
				return;
			case RfcPackage.SERVER_DATA_STORE__SERVER_DATA:
				getServerData().clear();
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
			case RfcPackage.SERVER_DATA_STORE__ENTRIES:
				return entries != null && !entries.isEmpty();
			case RfcPackage.SERVER_DATA_STORE__SERVER_DATA:
				return serverData != null && !serverData.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ServerDataStoreImpl
