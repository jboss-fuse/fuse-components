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
package com.redhat.camel.component.cics.routes;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.apache.camel.ExchangePattern.*;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_ENCODING;
import static com.redhat.camel.component.cics.CICSConstants.CICS_RC_HEADER;
import static org.junit.jupiter.api.Assertions.*;


/**
 * It is needed to change the properties CTG_XXX to test successfully
 * 
 * @author Sergio Gutierrez (sgutierr@redhat.com)
 * @author Jose Roman Martin Gil (rmarting@redhat.com)
 * @author Luigi De Masi (ldemasi@redhat.com)
 *
 */
@Testcontainers
public class RouteTest extends CamelTestSupport {

	private final static Network network = Network.newNetwork();

	@Container
    private static final GenericContainer<?> ctgContainer = new GenericContainer<>("images.paas.redhat.com/fuseqe/ibm-cicstg-container-linux-x86-trial:9.3")
			.withEnv("LICENSE","accept")
			.withNetwork(network)
			.withNetworkAliases("cgt")
			.withExposedPorts(2006, 2810)
			.withCopyFileToContainer(MountableFile.forClasspathResource("ctg.ini"), "/var/cicscli/ctg.ini")
			.waitingFor(Wait.forLogMessage(".*CTG6512I CICS Transaction Gateway initialization complete.*",1))
			.withStartupTimeout(Duration.ofSeconds(60L));

	public static final Logger LOG = LoggerFactory.getLogger(RouteTest.class);


	/** CTG Server */
	private static final String CTG_SERVER = "cics_server_name";


	@Test
	public void testECIREADY() throws Exception {
		assertTrue(ctgContainer.isRunning());

		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);

		Map headers = new HashMap<>();
		headers.put("programName", "ECIREADY");
		headers.put("commAreaSize", "18");

		// template.setDefaultEndpointUri("direct:start");
		Object returned = template.sendBodyAndHeaders("direct:test", InOut, null, headers);

		mock.setExpectedMessageCount(1);
		//assertMockEndpointsSatisfied();

		Exchange ex = mock.getExchanges().get(0);

		Assertions.assertEquals(0, ex.getIn().getHeader(CICS_RC_HEADER));

	}

	@Test
	public void testECIB2() throws Exception {
		assertTrue(ctgContainer.isRunning());

		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);

		Map headers = new HashMap<>();

		headers.put("programName", "ECIREADY");
		headers.put("commAreaSize", "50");

		Object s = template.sendBodyAndHeaders("direct:test", InOut,null, headers);

		mock.setExpectedMessageCount(1);

		Exchange ex = mock.getExchanges().get(0);

		LOG.info("response: {}", new String((byte[]) ex.getIn().getBody(),CICS_DEFAULT_ENCODING));

		Assertions.assertEquals(0, ex.getIn().getHeader(CICS_RC_HEADER));
	}




	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		int  CTG_PORT = ctgContainer.getMappedPort(2006);
		String CTG_HOST =  ctgContainer.getHost();

		return new RouteBuilder() {
			public void configure() {
				from("direct:test")
						.log("CTG Endpoing: cics://" + CTG_HOST + ":" + CTG_PORT + "/" + CTG_SERVER)
						.log("Calling ${headers.programName} Program")
						.to("cics://" + CTG_HOST + ":" + CTG_PORT + "/" + CTG_SERVER + "?sslKeyring=sslKeyring1&sslPassword=sslPassword1&userId=userId1&password=password1")
						.log("Called ${headers.programName} Program")
						.to("mock:result");
			}
		};
	}
}