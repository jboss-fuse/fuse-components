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

import org.apache.camel.Converter;
import org.eclipse.emf.ecore.EObject;
import org.fusesource.camel.component.sap.model.idoc.DocumentList;
import org.fusesource.camel.component.sap.model.idoc.impl.DocumentListImpl;
import org.fusesource.camel.component.sap.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Type Converter for SAP document list objects.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
@Converter
public enum DocumentListConverter {
	INSTANCE;
	
	private static final Logger LOG = LoggerFactory.getLogger(DocumentListConverter.class);

	@Converter(allowNull = true)
	public static DocumentList toDocumentList(String string) {
		try {
			EObject eObject = Util.unmarshal(string);
			
			if (DocumentListImpl.class.isInstance(eObject)) {
				return (DocumentListImpl) eObject;
			}
		} catch (IOException e) {
			// Ignore
			LOG.warn("Failed to convert String to DocumentList", e);
		} 
		return null; 
	}

	@Converter(allowNull = true)
	public static DocumentList toDocumentList(InputStream in) {
		try {
			EObject eObject = Util.fromInputStream(in);
			
			if (DocumentListImpl.class.isInstance(eObject)) {
				return (DocumentListImpl) eObject;
			}
		} catch (IOException e) {
			// Ignore
			LOG.warn("Failed to convert InputStream to DocumentList", e);
		} 
		return null; 
	}

	@Converter(allowNull = true)
	public static DocumentList toDocumentList(byte[] byteArray) {
		try {
			EObject eObject = Util.unmarshal(new String(byteArray));
			
			if (DocumentListImpl.class.isInstance(eObject)) {
				return (DocumentListImpl) eObject;
			}
		} catch (IOException e) {
			// Ignore
			LOG.warn("Failed to convert byte array to DocumentList", e);
		} 
		return null; 
	}

	@Converter(allowNull = true)
	public static String toString(DocumentListImpl documentList) {
		try {
			return Util.marshal(documentList);
		} catch (IOException e) {
			LOG.warn("Failed to convert DocumentList to String", e);
			return null;
		}
	}
	
	@Converter(allowNull = true)
	public static OutputStream toOutputStream(DocumentListImpl documentList) {
		try {
			return Util.toOutputStream(documentList);
		} catch (IOException e) {
			LOG.warn("Failed to convert DocumentList to OutputStream", e);
			return null;
		}
	}
	
	@Converter(allowNull = true)
	public static InputStream toInputStream(DocumentListImpl documentList) {
		try {
			return Util.toInputStream(documentList);
		} catch (IOException e) {
			LOG.warn("Failed to convert DocumentList to InputStream", e);
			return null;
		}
	}
	
}
