package com.redhat.camel.component.cics;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.redhat.camel.component.cics.CICSConstants.CICS_COMM_AREA_SIZE_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_ENCODING_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_PROGRAM_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_RETURN_CODE_HEADER;
import static java.lang.Thread.sleep;
import static org.apache.camel.ExchangePattern.InOut;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomEncodingTest extends AbstractCICSTest {

    public static final Logger LOG = LoggerFactory.getLogger(CustomEncodingTest.class);


    @Test
    public void testECIREADYwithEncodingHeader() throws Exception {
        for (int i =0; i< 10; i++){
            if(ctgContainer.isRunning()){
                break;
            }
            sleep(5000);
        }
        assertTrue(ctgContainer.isRunning());
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        Map headers = new HashMap();
        headers.put(CICS_PROGRAM_NAME_HEADER, "ECIREADY");
        headers.put(CICS_COMM_AREA_SIZE_HEADER, "18");
        headers.put(CICS_ENCODING_HEADER, "US-ASCII");
        Object returned = template.sendBodyAndHeaders("direct:test", InOut, "Hello from Camel!", headers);
        mock.setExpectedMessageCount(1);
        Exchange ex = mock.getExchanges().get(0);
        Assertions.assertEquals(0, ex.getIn().getHeader(CICS_RETURN_CODE_HEADER));
    }


    @Override
    protected String getOptions(String host, int port) {
        return "?host="+host+"&port="+port+"&protocol=tcp&encoding=UTF8";
    }
}
