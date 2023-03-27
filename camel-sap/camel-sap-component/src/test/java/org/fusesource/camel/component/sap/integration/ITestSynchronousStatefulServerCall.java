package org.fusesource.camel.component.sap.integration;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.fusesource.camel.component.sap.model.rfc.Request;
import org.fusesource.camel.component.sap.util.Util;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Integration test cases for server RFC calls.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
@Disabled("These tests are meant to be run manually (see infinite while loop in test method)")
public class ITestSynchronousStatefulServerCall extends CamelSpringTestSupport {

	@Test
	public void test() throws Exception {
		while(true) {
			Request request = (Request) consumer.receiveBody("direct:out");
			Util.print(request);
		}
	}

	@Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("sap-srfc-server:nplServer:PARAM_TEST").to("bean:counter").to("direct:out");
            }
        };
    }

	@Override
	protected ClassPathXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"org/fusesource/camel/component/sap/integration/ITestCallConfig.xml");
	}

}
