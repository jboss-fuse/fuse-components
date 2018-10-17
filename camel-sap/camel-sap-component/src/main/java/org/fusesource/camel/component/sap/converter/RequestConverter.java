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
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 */
package org.fusesource.camel.component.sap.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.CamelException;
import org.apache.camel.Converter;
import org.eclipse.emf.ecore.EObject;
import org.fusesource.camel.component.sap.model.rfc.Request;
import org.fusesource.camel.component.sap.model.rfc.impl.RequestImpl;
import org.fusesource.camel.component.sap.util.Util;

/**
 * A Type Converter for SAP Request objects.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
@Converter
public enum RequestConverter {
	INSTANCE;
	
	@Converter
	public static Request toRequest(String string) throws CamelException {
		try {
			EObject eObject = Util.unmarshal(string);
			
			if (RequestImpl.class.isInstance(eObject)) {
				return (RequestImpl) eObject;
			} else {
				throw new CamelException("Failed to convert String to Request");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert String to Request", e);
		} 
	}

	@Converter
	public static Request toRequest(InputStream in) throws CamelException {
		try {
			EObject eObject = Util.fromInputStream(in);
			
			if (RequestImpl.class.isInstance(eObject)) {
				return (RequestImpl) eObject;
			} else {
				throw new CamelException("Failed to convert InputStream to Request");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert InputStream to Request", e);
		} 
	}

	@Converter
	public static Request toRequest(byte[] byteArray) throws CamelException {
		try {
			EObject eObject = Util.unmarshal(new String(byteArray));
			
			if (RequestImpl.class.isInstance(eObject)) {
				return (RequestImpl) eObject;
			} else {
				throw new CamelException("Failed to convert byte array to Request");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert byte array to Request", e);
		} 
	}

	@Converter
	public static String toString(RequestImpl structure) throws CamelException {
		try {
			return Util.marshal(structure);
		} catch (IOException e) {
			throw new CamelException("Failed to convert Request to String", e);
		}
	}
	
	@Converter
	public static OutputStream toOutputStream(RequestImpl structure) throws CamelException {
		try {
			return Util.toOutputStream(structure);
		} catch (IOException e) {
			throw new CamelException("Failed to convert Request to OutputStream", e);
		}
	}
	
	@Converter
	public static InputStream toInputStream(RequestImpl structure) throws CamelException {
		try {
			return Util.toInputStream(structure);
		} catch (IOException e) {
			throw new CamelException("Failed to convert Request to InputStream", e);
		}
	}
	
}
