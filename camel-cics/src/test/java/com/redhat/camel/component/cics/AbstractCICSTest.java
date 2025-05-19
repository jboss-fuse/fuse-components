package com.redhat.camel.component.cics;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.redhat.camel.component.cics.CICSConstants.CICS_COMM_AREA_SIZE_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_ENCODING;
import static com.redhat.camel.component.cics.CICSConstants.CICS_PROGRAM_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_RETURN_CODE_HEADER;
import static org.apache.camel.ExchangePattern.InOut;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractCICSTest extends AbstractCICSContainerizedTest {

    public static final Logger LOG = LoggerFactory.getLogger(AbstractCICSTest.class);

    protected static final String CTG_SERVER = "cics_server_name";

    @Test
    public void testECIREADY() throws Exception {
        assertTrue(ctgContainer.isRunning());
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        Map headers = new HashMap<>();
        headers.put(CICS_PROGRAM_NAME_HEADER, "ECIREADY");
        headers.put(CICS_COMM_AREA_SIZE_HEADER, "18");
        Object returned = template.sendBodyAndHeaders("direct:test", InOut, null, headers);
        mock.setExpectedMessageCount(1);
        Exchange ex = mock.getExchanges().get(0);
        Assertions.assertEquals(0, ex.getIn().getHeader(CICS_RETURN_CODE_HEADER));
    }

    @Test
    public void testECIB2() throws Exception {
        assertTrue(ctgContainer.isRunning());
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        Map headers = new HashMap<>();
        headers.put(CICS_PROGRAM_NAME_HEADER, "ECIREADY");
        headers.put(CICS_COMM_AREA_SIZE_HEADER, "18");
        Object s = template.sendBodyAndHeaders("direct:test", InOut,null, headers);
        mock.setExpectedMessageCount(1);
        Exchange ex = mock.getExchanges().get(0);
        LOG.info("response: {}", new String((byte[]) ex.getIn().getBody(),CICS_DEFAULT_ENCODING));
        Assertions.assertEquals(0, ex.getIn().getHeader(CICS_RETURN_CODE_HEADER));
    }



    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        int  CTG_PORT = ctgContainer.getMappedPort(2006);
        String CTG_HOST =  ctgContainer.getHost();

        return new RouteBuilder() {
            public void configure() {
                from("direct:test")
                        .log("CTG Endpoing: cics://eci"+getOptions(CTG_HOST,CTG_PORT))
                        .log("Calling ${headers.programName} Program")
                        .to("cics:eci"+getOptions(CTG_HOST,CTG_PORT))
                        .log("Called ${headers.programName} Program")
                        .to("mock:result");
            }
        };
    }

    protected abstract String getOptions(String host, int port);

}
