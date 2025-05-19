package com.redhat.camel.component.cics;

import com.ibm.ctg.client.ECIRequest;
import com.redhat.camel.component.cics.binding.CICSChannelEciBinding;
import com.redhat.camel.component.cics.binding.CICSCommAreaEciBinding;
import org.apache.camel.Exchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static com.redhat.camel.component.cics.CICSConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.ibm.ctg.client.ECIRequest.ECI_SYNC_TPN;

public class CICSCallTypeTest extends CamelTestSupport {

    private static final String URI = "cics://eci?host=localhost&port=12345&protocol=tcp&encoding=UTF8";

    private CICSEndpoint getEndpointWithoutCallType() {
        return (CICSEndpoint) context.getEndpoint(URI);
    }

    private CICSEndpoint getEndpointWithCallType() {
        return (CICSEndpoint) context.getEndpoint(URI + "&callType=" + ECI_SYNC_TPN);
    }

    @Test
    public void callTypeOnConfigurationTest() throws Exception {
        CICSEndpoint endpoint = getEndpointWithCallType();
        assertThat(endpoint.getConfiguration().getCallType()).isEqualTo(ECI_SYNC_TPN);
    }

    @Test
    public void callTypeChannelBindingTest() throws Exception {
        CICSEndpoint endpoint = getEndpointWithCallType();// Test Channel Binding
        Exchange exchange = endpoint.createExchange();
        exchange.getMessage().setHeader(CICS_CHANNEL_NAME_HEADER, "test");
        exchange.getMessage().setHeader(CICS_CONTAINER_NAME_HEADER, "test");

        exchange.getMessage().setBody("test");

        CICSChannelEciBinding channelEciBinding = new CICSChannelEciBinding();
        ECIRequest request = channelEciBinding.toECIRequest(exchange, endpoint.getConfiguration());
        assertThat(request.getCallType()).isEqualTo(ECI_SYNC_TPN);
    }

    // Test COMMAREA Binding
    @Test
    public void callTypeCommareaBindingTest() throws Exception {
        CICSEndpoint endpoint = getEndpointWithCallType();// Test Channel Binding
        Exchange exchange = endpoint.createExchange();
        exchange.getMessage().setBody("test");
        CICSCommAreaEciBinding commareaEciBinding = new CICSCommAreaEciBinding();
        ECIRequest request = commareaEciBinding.toECIRequest(exchange, endpoint.getConfiguration());
        assertThat(request.getCallType()).isEqualTo(ECI_SYNC_TPN);
    }

    @Test
    public void callTypeChannelBindingHeaderTest() throws Exception {
        CICSEndpoint endpoint = getEndpointWithoutCallType();// Test Channel Binding
        Exchange exchange = endpoint.createExchange();
        exchange.getMessage().setHeader(CICS_CHANNEL_NAME_HEADER, "test");
        exchange.getMessage().setHeader(CICS_CONTAINER_NAME_HEADER, "test");

        exchange.getMessage().setBody("test");
        exchange.getMessage().setHeader(CICS_CALL_TYPE_HEADER, ECI_SYNC_TPN);

        CICSChannelEciBinding channelEciBinding = new CICSChannelEciBinding();
        ECIRequest request = channelEciBinding.toECIRequest(exchange, endpoint.getConfiguration());
        assertThat(request.getCallType()).isEqualTo(ECI_SYNC_TPN);
    }


    @Test
    public void callTypeCommareaBindingWithHeaderTest() throws Exception {
        CICSEndpoint endpoint = getEndpointWithoutCallType();// Test commarea Binding
        Exchange exchange = endpoint.createExchange();
        exchange.getMessage().setBody("test");
        exchange.getMessage().setHeader(CICS_CALL_TYPE_HEADER, ECI_SYNC_TPN);
        CICSCommAreaEciBinding commareaEciBinding = new CICSCommAreaEciBinding();
        ECIRequest request = commareaEciBinding.toECIRequest(exchange, endpoint.getConfiguration());
        assertThat(request.getCallType()).isEqualTo(ECI_SYNC_TPN);
    }

}
