/**
 * Copyright 2018 Red Hat, Inc.
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

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.fusesource.camel.component.sap.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.jco.JCo;

/**
 * An SAP producer performing a clearing of JCo Repository meta-data and Data Layer registry meta-data. 
 * 
 * <p><b>WARNING: These producer types are to be used in non-production environments ONLY!</b> 
 * <p><b>Deployment and use of these producer types in production environments are not supported.</b>
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
public class SapClearCacheProducer extends DefaultProducer {

	private static final transient Logger LOG = LoggerFactory.getLogger(SapClearCacheProducer.class);

	public SapClearCacheProducer(SapClearCacheEndpoint endpoint) {
		super(endpoint);
	}

	@Override
	public void process(Exchange exchange) throws Exception {

		for (String repositoryId: JCo.getRepositoryIDs()) {
			JCo.getRepository(repositoryId).clear();
		}
		Util.registry.clear();;
		Util.ensureBasePackages();
		
		LOG.debug("Cleared JCo repositories and Data Layer registry");
		
		exchange.setOut(exchange.getIn().copy());
	}
	
	@Override
	public SapClearCacheEndpoint getEndpoint() {
		return (SapClearCacheEndpoint) super.getEndpoint();
	}

}
