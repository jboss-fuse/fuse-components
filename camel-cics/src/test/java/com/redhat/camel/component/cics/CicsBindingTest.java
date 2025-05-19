package com.redhat.camel.component.cics;

import com.ibm.ctg.client.ECIRequest;
import com.redhat.camel.component.cics.binding.CICSChannelEciBinding;
import com.redhat.camel.component.cics.binding.CICSCommAreaEciBinding;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;

// Tests for CSB-4525
public class CicsBindingTest extends CamelTestSupport {

    CICSConfiguration configuration = new CICSConfiguration();


    @Test
    public void channelResetBodyIfTransactionFailed() throws Exception {
        CICSChannelEciBinding binding = new CICSChannelEciBinding();
        ECIRequest request = new ECIRequest();
        request.Cics_Rc = 23;
        Exchange exchange = new DefaultExchange(context());
        Map in = new HashMap();
        exchange.getMessage().setBody(in);
        binding.toExchange(request,exchange, 23, configuration);
        assertNull(exchange.getMessage().getBody());
    }

    @Test
    public void commareaResetBodyIfTransactionFailed() throws UnsupportedEncodingException {
        CICSCommAreaEciBinding binding = new CICSCommAreaEciBinding();
        ECIRequest request = new ECIRequest();
        request.Cics_Rc = 10;
        Exchange exchange = new DefaultExchange(context());
        byte[] in = new byte[12];
        exchange.getMessage().setBody(in);
        binding.toExchange(request,exchange, 13, configuration);
        assertNull(exchange.getMessage().getBody());
    }

    private void configure(){
        configuration.setServer("localhost");
        configuration.setUserId("user");
        configuration.setPassword("pwd");
    }
}
