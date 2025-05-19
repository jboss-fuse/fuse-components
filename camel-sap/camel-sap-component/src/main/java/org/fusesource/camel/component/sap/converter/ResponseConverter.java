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
import org.fusesource.camel.component.sap.model.rfc.Response;
import org.fusesource.camel.component.sap.model.rfc.impl.ResponseImpl;
import org.fusesource.camel.component.sap.util.Util;

/**
 * A Type Converter for SAP Response objects.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
@Converter(generateBulkLoader = true)
public enum ResponseConverter {
	INSTANCE;
	
	@Converter()
	public static Response toResponse(String string) throws CamelException {
		try {
			EObject eObject = Util.unmarshal(string);
			
			if (ResponseImpl.class.isInstance(eObject)) {
				return (ResponseImpl) eObject;
			} else {
				throw new CamelException("Failed to convert String to Response");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert String to Response", e);
		} 
	}

	@Converter()
	public static Response toResponse(InputStream in) throws CamelException {
		try {
			EObject eObject = Util.fromInputStream(in);
			
			if (ResponseImpl.class.isInstance(eObject)) {
				return (ResponseImpl) eObject;
			} else {
				throw new CamelException("Failed to convert InputStream to Response");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert InputStream to Response", e);
		} 
	}

	@Converter()
	public static Response toResponse(byte[] byteArray) throws CamelException {
		try {
			EObject eObject = Util.unmarshal(new String(byteArray));
			
			if (ResponseImpl.class.isInstance(eObject)) {
				return (ResponseImpl) eObject;
			} else {
				throw new CamelException("Failed to convert byte array to Response");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert byte array to Response", e);
		} 
	}

	@Converter()
	public static String toString(ResponseImpl structure) throws CamelException {
		try {
			return Util.marshal(structure);
		} catch (IOException e) {
			throw new CamelException("Failed to convert Response to String", e);
		}
	}
	
	@Converter()
	public static OutputStream toOutputStream(ResponseImpl structure) throws CamelException {
		try {
			return Util.toOutputStream(structure);
		} catch (IOException e) {
			throw new CamelException("Failed to convert Response to OutputStream", e);
		}
	}
	
	@Converter()
	public static InputStream toInputStream(ResponseImpl structure) throws CamelException {
		try {
			return Util.toInputStream(structure);
		} catch (IOException e) {
			throw new CamelException("Failed to convert Response to InputStream", e);
		}
	}
	
}
