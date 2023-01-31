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

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An SAP component that manages {@link SapClearCacheEndpoint}.
 * 
 * <p><b>WARNING: The endpoints of this component are to be used in non-production environments ONLY!</b> 
 * <p><b>Deployment and use of this component and its endpoints in production environments are not supported.</b>
 * 
 * @author William Collins <punkhornsw@gmail.com>
 * 
 */
@SuppressWarnings("deprecation")
public class SapClearCacheComponent extends DefaultComponent {

	private static final Logger LOG = LoggerFactory.getLogger(SapClearCacheComponent.class);
	
	public SapClearCacheComponent() {
		super();
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		if (!uri.startsWith("sap-clear-cache")) { 
			throw new IllegalArgumentException("The URI '" +  uri + "' has invalid scheme; should be 'sap-clear-cache'");			
		}
		SapClearCacheEndpoint endpoint = new SapClearCacheEndpoint(uri, this);

		// Configure Endpoint
		setProperties(endpoint, parameters);
		LOG.debug("Created endpoint '" + uri + "'");
		
		return endpoint;
	}

	@Override
    protected void doStart() throws Exception {
    	super.doStart();
    	LOG.debug("STARTED");
    }
    
    @Override
    protected void doStop() throws Exception {
    	super.doStop();
    	LOG.debug("STOPPED");
    }
}
