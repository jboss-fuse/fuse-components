/**
 * Copyright 2014 Red Hat, Inc.
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
import org.fusesource.camel.component.sap.model.idoc.Document;
import org.fusesource.camel.component.sap.model.idoc.impl.DocumentImpl;
import org.fusesource.camel.component.sap.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Type Converter for SAP document objects.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
@Converter
public enum DocumentConverter {
	INSTANCE;
	
	@Converter()
	public static Document toDocument(String string) throws CamelException {
		try {
			EObject eObject = Util.unmarshal(string);
			
			if (DocumentImpl.class.isInstance(eObject)) {
				return (DocumentImpl) eObject;
			} else {
				throw new CamelException("Failed to convert String to Document");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert String to Document", e);
		} 
	}

	@Converter()
	public static Document toDocument(InputStream in) throws CamelException {
		try {
			EObject eObject = Util.fromInputStream(in);
			
			if (DocumentImpl.class.isInstance(eObject)) {
				return (DocumentImpl) eObject;
			} else {
				throw new CamelException("Failed to convert InputStream to Document");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert InputStream to Document", e);
		} 
	}

	@Converter()
	public static Document toDocument(byte[] byteArray) throws CamelException {
		try {
			EObject eObject = Util.unmarshal(new String(byteArray));
			
			if (DocumentImpl.class.isInstance(eObject)) {
				return (DocumentImpl) eObject;
			} else {
				throw new CamelException("Failed to convert byte array to Document");
			}
		} catch (IOException e) {
			throw new CamelException("Failed to convert byte array to Document", e);
		} 
	}

	@Converter()
	public static String toString(DocumentImpl document) throws CamelException {
		try {
			return Util.marshal(document);
		} catch (IOException e) {
			// Ignore
			throw new CamelException("Failed to convert Document to String", e);
		}
	}
	
	@Converter()
	public static OutputStream toOutputStream(DocumentImpl document) throws CamelException {
		try {
			return Util.toOutputStream(document);
		} catch (IOException e) {
			// Ignore
			throw new CamelException("Failed to convert Document to OutputStream", e);
		}
	}
	
	@Converter()
	public static InputStream toInputStream(DocumentImpl document) throws CamelException {
		try {
			return Util.toInputStream(document);
		} catch (IOException e) {
			// Ignore
			throw new CamelException("Failed to convert Document to InputStream", e);
		}
	}
	
}
