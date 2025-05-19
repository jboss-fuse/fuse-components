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

package com.redhat.camel.component.cics;

import com.redhat.camel.component.cics.pool.CICSPooledGatewayFactory;
import com.redhat.camel.component.cics.pool.CICSSingleGatewayFactory;
import org.apache.camel.spi.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * It is needed to change the properties CTG_XXX to test successfully
 *
 * @author Luigi De Masi (ldemasi@redhat.com)
 *
 */
@Testcontainers
public class PooledConnectionFactoryTest extends AbstractCICSTest {
	public static final Logger LOG = LoggerFactory.getLogger(PooledConnectionFactoryTest.class);

	@Override
	protected void bindToRegistry(Registry registry) throws Exception {
		int  CTG_PORT = ctgContainer.getMappedPort(2006);
		String CTG_HOST =  ctgContainer.getHost();
		CICSSingleGatewayFactory factory = new CICSSingleGatewayFactory();
		factory.setHost(CTG_HOST);
		factory.setPort(CTG_PORT);
		factory.setProtocol("tcp");
		//factory.setSer
		CICSPooledGatewayFactory gatewayPool = new CICSPooledGatewayFactory(factory);
		gatewayPool.setMaxTotal(50);
		gatewayPool.setMinIdle(25);
		gatewayPool.setInitialPoolSize(30);
		gatewayPool.setJmxEnabled(true);
		gatewayPool.start();
		registry.bind("factory", gatewayPool);
	}

	@Override
	protected String getOptions(String host, int port) {
		return "?gatewayFactory=#factory";
	}
}