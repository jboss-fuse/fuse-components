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

import org.apache.camel.Category;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriPath;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An SAP endpoint providing an endpoint to clear meta-data from JCo Repository and Data Layer.
 * 
 * <p><b>WARNING: These endpoint types are to be used in non-production environments ONLY!</b> 
 * <p><b>Deployment and use of these endpoint types in production environments are not supported.</b>
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
@UriEndpoint(firstVersion = "3.20.1-redhat", scheme="sap-clear-cache", syntax = "sap-clear-cache", producerOnly = true, title="SAP Clear Cache", category = { Category.SAP })
public class SapClearCacheEndpoint extends DefaultEndpoint {
	
    private static final Logger LOG = LoggerFactory.getLogger(SapClearCacheEndpoint.class);

	@UriPath
	@Metadata
	String name;

	public SapClearCacheEndpoint() {
	}

	public SapClearCacheEndpoint(String endpointUri, SapClearCacheComponent component) {
		super(endpointUri, component);
	}

	@Override
	public Producer createProducer() throws Exception {
		LOG.debug("Created producer for endpoint '" + getEndpointUri() + "'");
		return new SapClearCacheProducer(this);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		throw new UnsupportedOperationException(
				"Endpoint '"  + getEndpointUri() + "' does not support consumers");
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * Logical Endpoint name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Logical Endpoint name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
