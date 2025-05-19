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
import org.apache.camel.support.DefaultComponent;
import org.fusesource.camel.component.sap.model.rfc.RepositoryData;
import org.fusesource.camel.component.sap.util.ComponentRepositoryDataProvider;
import org.fusesource.camel.component.sap.util.RfcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.jco.server.JCoServer;

/**
 * An SAP component that manages {@link SapTransactionalRfcServerEndpoint}
 * .
 * 
 * @author William Collins <punkhornsw@gmail.com>
 * 
 */
public abstract class SapRfcServerComponent extends DefaultComponent {

	public SapRfcServerComponent(Class<? extends Endpoint> endpointClass) {
		super();
	}

	protected FunctionHandlerFactory getServerHandlerFactory(String serverName) throws Exception {
		JCoServer server = getServer(serverName);
		if (server == null) {
			return null;
		}
		return (FunctionHandlerFactory) server.getCallHandlerFactory();
	}

	protected JCoServer getServer(String serverName) throws Exception {
		return ServerManager.INSTANCE.getServer(serverName);
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		ServerManager.INSTANCE.incrementActiveComponentInstances();
	}
	
	@Override
	protected void doStop() throws Exception {
		ServerManager.INSTANCE.decrementActiveComponentInstances();
		super.doStop();
	}
}
