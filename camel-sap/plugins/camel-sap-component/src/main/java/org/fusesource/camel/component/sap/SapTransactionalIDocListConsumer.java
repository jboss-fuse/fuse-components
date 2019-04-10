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
package org.fusesource.camel.component.sap;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fusesource.camel.component.sap.model.idoc.DocumentList;
import org.fusesource.camel.component.sap.util.IDocUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.idoc.IDocDocumentList;
import com.sap.conn.idoc.IDocRuntimeException;
import com.sap.conn.idoc.jco.JCoIDocHandler;
import com.sap.conn.jco.server.JCoServerContext;

/**
 * An SAP consumer receiving an IDoc (Intermediate Document) list from an SAP system. 
 * 
 * @author William Collins <punkhornsw@gmail.com>
 * 
 */
public class SapTransactionalIDocListConsumer extends SapConsumer implements JCoIDocHandler {

	private static final Logger LOG = LoggerFactory.getLogger(SapTransactionalIDocListConsumer.class);

	public SapTransactionalIDocListConsumer(Endpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	@Override
	public SapTransactionalIDocListServerEndpoint getEndpoint() {
		return (SapTransactionalIDocListServerEndpoint) super.getEndpoint();
	}

	@Override
	public void handleRequest(JCoServerContext serverContext, IDocDocumentList idocDocumentList) {
		
		Exchange exchange = getEndpoint().createExchange(ExchangePattern.InOnly);
		
		try {

			if (LOG.isDebugEnabled()) {
				LOG.debug("Handling IDoc document {}", idocDocumentList.toString());
			}

			// Create and populate document list
			DocumentList documentList = IDocUtil.createDocumentList(getEndpoint().getServer().getIDocRepository(), idocDocumentList.getIDocType(), idocDocumentList.getIDocTypeExtension(), idocDocumentList.getSystemRelease(), idocDocumentList.getApplicationRelease());
			IDocUtil.extractIDocDocumentListIntoDocumentList(idocDocumentList, documentList);
			
			// Populate SAP exchange properties
			SapExchangePropertiesUtil.addServerPropertiesToExchange(getEndpoint(), exchange);
			
			Message message = exchange.getIn();

			// Populate exchange properties
			if (isStateful()) {
				exchange.setProperty(SAP_SESSION_CONTEXT_PROPERTY_NAME, sessionContext);
			}

			// Populate message headers
			SapMessageHeadersUtil.addSapHeadersToMessage(getEndpoint(), message);

			// Populated exchange message
			message.setBody(documentList);

			// Process exchange
			getProcessor().process(exchange);
			
		} catch (Exception e) {
			if(getEndpoint().isPropagateExceptions()) {
				throw new IDocRuntimeException(e.getMessage(), e);
			} else {
				getExceptionHandler().handleException("Failed to process document list", e);
			}
		}
		
		if (exchange.getException() != null && getEndpoint().isPropagateExceptions()) {
			throw new IDocRuntimeException(exchange.getException().getMessage(), exchange.getException());
		}
	}
}
