package com.redhat.camel.component.cics;

import com.ibm.ctg.client.Channel;import com.ibm.ctg.client.Container;
import com.ibm.ctg.client.ECIRequest;
import com.redhat.camel.component.cics.binding.CICSChannelEciBinding;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ibm.ctg.client.Container.ContainerType.*;
import static com.ibm.ctg.client.ECIRequest.*;
import static com.ibm.ctg.client.ECIRequest.ECI_NO_EXTEND;
import static com.redhat.camel.component.cics.CICSConstants.CICS_CHANNEL_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_CONTAINER_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_EXTEND_MODE_HEADER;import static com.redhat.camel.component.cics.CICSConstants.CICS_LUW_TOKEN_HEADER;import static com.redhat.camel.component.cics.CICSConstants.CICS_PASSWORD_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_PROGRAM_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_RETURN_CODE_HEADER;import static com.redhat.camel.component.cics.CICSConstants.CICS_SERVER_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_TRANSACTION_ID_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_USER_ID_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;import static org.junit.jupiter.api.Assertions.assertTrue;

public class CICSChannelEciBindingTest extends CamelTestSupport {

    CICSConfiguration configuration = new CICSConfiguration();

    CICSChannelEciBinding  binding = new CICSChannelEciBinding();

    @Override
    public void doPostSetup(){
        configuration.setServer("CEDEXA1I");
        configuration.setUserId("user01");
        configuration.setPassword("foobar01");
    }

    @Test
    public void singleInputContainerTest() throws Exception {


        String expectedChannelName = "mychannel";
        String expectedContainerName = "myContainer";
        String expectedUserId = "user12";
        String expectedPassword = "fooBar12";
        String expectedTransactionId = "1";
        String expectedContainerValue = "My Container Content";
        String expectedServerName = "milkyWay90";

        Exchange exchange = new DefaultExchange(context);
        Message message = exchange.getMessage();
        message.setHeader(CICS_PROGRAM_NAME_HEADER, "ECIREADY");
        message.setHeader(CICS_TRANSACTION_ID_HEADER, expectedTransactionId);
        message.setHeader(CICS_CHANNEL_NAME_HEADER, expectedChannelName);
        message.setHeader(CICS_CONTAINER_NAME_HEADER, expectedContainerName);
        message.setHeader(CICS_USER_ID_HEADER, expectedUserId);
        message.setHeader(CICS_PASSWORD_HEADER, expectedPassword);
        message.setHeader(CICS_SERVER_HEADER, expectedServerName);
        message.setBody(expectedContainerValue);

        ECIRequest request = binding.toECIRequest(exchange, configuration);
        Container container = request.channel.getContainers().stream().toList().get(0);

        assertEquals(expectedChannelName, request.channel.getName() );
        assertEquals(1, request.channel.getContainers().size());
        assertEquals(expectedContainerName, container.getName());
        assertEquals(expectedTransactionId, request.Transid);
        assertEquals(expectedUserId, request.Userid);
        assertEquals(expectedPassword, request.Password);
        assertEquals(CHAR, container.getType());
        assertEquals(expectedContainerValue, container.getCHARData());
        assertEquals(expectedServerName, request.Server);
    }

    @Test
    public void multipleInputContainersTest() throws Exception {
        String expectedChannelName = "mychannel";

        Map<String, Object> containers = new HashMap<>();
        containers.put("FirstChar", "My First Container");
        containers.put("SecondByteArray", "My Second Container".getBytes());

        Exchange exchange = new DefaultExchange(context);
        Message message = exchange.getMessage();
        message.setHeader(CICS_CHANNEL_NAME_HEADER, expectedChannelName);
        message.setBody(containers);

        ECIRequest request = binding.toECIRequest(exchange, configuration);
        List<Container> actualContainers = request.channel.getContainers().stream().toList();
        Container first = actualContainers.get(0);
        Container second = actualContainers.get(1);

        assertEquals(configuration.getServer(), request.Server);
        assertEquals(2, request.channel.getContainers().size());
        assertEquals(expectedChannelName, request.channel.getName() );

        assertEquals("FirstChar", first.getName());
        assertEquals(CHAR, first.getType());
        assertEquals("My First Container", first.getCHARData());

        assertEquals("SecondByteArray", second.getName());
        assertEquals(BIT, second.getType());
        assertEquals("My Second Container", new String(second.getBITData()));

        assertEquals(configuration.getUserId(), request.Userid);
        assertEquals(configuration.getPassword(), request.Password);
    }


    @Test
    public void resultcontainersBindingTest() throws Exception {

        Channel channel = new Channel("mychannel");
        String inputData = "viNMTJiRhKsqIVE8/vghZhtBnEnU3GnDjj5x79gE8F+iWO5XBRmkpGghA0hcp2qOdwZcDUUfWJH0jVSSRx0pGUh0gDPWDtERSMWmFKDp19YHnq49YA4Ex/YWYIg52oG1MzBJAGfaLiBx1kecYUU1zw03UYZq+QZcTtEJ/YilaNI=";
        byte[] inputByteData = "This should be an example of byte data".getBytes();

        channel.createContainer("INPUT_DATA", inputData);
        channel.createContainer("INPUT_BYTE_DATA", inputByteData);
        ECIRequest request  = new ECIRequest(
                ECI_SYNC,        //ECI call type
                "EPI01",         //CICS server
                null,            //CICS username
                null,            //CICS password
                "EC03",          //Program to run
                null,            //Transaction to run
                channel,         //Channel
                ECI_NO_EXTEND,   //ECI extend mode
                ECI_LUW_NEW      //ECI LUW token
        );

        assertTrue(request.hasChannel());
        Exchange exchange = new DefaultExchange(context);
        binding.toExchange(request, exchange, 0, new CICSConfiguration());

        Message message = exchange.getMessage();
        assertEquals(ECI_NO_ERROR, message.getHeader(CICS_RETURN_CODE_HEADER, Integer.class));
        assertEquals(ECI_NO_EXTEND, message.getHeader(CICS_EXTEND_MODE_HEADER, Integer.class));
        assertEquals(ECI_LUW_NEW, message.getHeader(CICS_LUW_TOKEN_HEADER, Integer.class));

        Map<String, Object> containers = message.getBody(Map.class);
        assertTrue(containers.containsKey("INPUT_DATA"));
        assertTrue(containers.containsKey("INPUT_BYTE_DATA"));

        assertEquals(inputData, containers.get("INPUT_DATA"));
        assertEquals(inputByteData, containers.get("INPUT_BYTE_DATA"));


    }

}
