package com.redhat.camel.component.cics.binding;

import com.ibm.ctg.client.Channel;
import com.ibm.ctg.client.Container;
import com.ibm.ctg.client.ECIRequest;
import com.ibm.ctg.client.exceptions.ContainerException;
import com.redhat.camel.component.cics.CICSConfiguration;
import com.redhat.camel.component.cics.CICSEciBinding;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.ibm.ctg.client.ECIRequest.ECI_LUW_NEW;
import static com.ibm.ctg.client.ECIRequest.ECI_NO_EXTEND;
import static com.ibm.ctg.client.ECIRequest.ECI_SYNC;
import static com.redhat.camel.component.cics.CICSConstants.CICS_CHANNEL_CCSID_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_CHANNEL_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_CONTAINER_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_ECI_REQUEST_TIMEOUT_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_EXTEND_MODE_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_LUW_TOKEN_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_PASSWORD_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_PROGRAM_NAME_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_SERVER_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_TRANSACTION_ID_HEADER;
import static com.redhat.camel.component.cics.CICSConstants.CICS_USER_ID_HEADER;
import static java.lang.Integer.parseInt;
import static org.apache.camel.support.ObjectHelper.isNumber;

public class CICSChannelEciBinding implements CICSEciBinding {

    private static final Logger LOGGER = LoggerFactory.getLogger(CICSChannelEciBinding.class);


    protected void createContainer(String name, Object value, Channel requestChannel) throws ContainerException, UnsupportedEncodingException {
        if (value instanceof String) {
            requestChannel.createContainer(name, (String) value);
            return;
        }
        if (value instanceof byte[]) {
            requestChannel.createContainer(name,  (byte[]) value);
            return;
        }
        throw new IllegalArgumentException("container " + name + ": container value must be a String or byte array");
    }

    protected void createContainers(Exchange exchange, Channel requestChannel) throws Exception {
        String containerName = exchange.getMessage().getHeader(CICS_CONTAINER_NAME_HEADER, String.class);
        Object data = exchange.getMessage().getBody();
        if (data != null && !Map.class.isAssignableFrom(data.getClass())) {
            createContainer(containerName, data, requestChannel);
            return;
        }
        Map<String, Object> containersMap = (Map<String, Object>) data;

        for (Map.Entry<String, Object> entry : containersMap.entrySet()) {
            createContainer(entry.getKey(), entry.getValue(), requestChannel);
        }
    }

    @Override
    public ECIRequest toECIRequest(Exchange exchange, CICSConfiguration configuration) throws Exception {
        Message inMessage = exchange.getMessage();
        String programName = inMessage.getHeader(CICS_PROGRAM_NAME_HEADER, String.class);
        String transactionId = inMessage.getHeader(CICS_TRANSACTION_ID_HEADER, String.class);
        String channelName = inMessage.getHeader(CICS_CHANNEL_NAME_HEADER, String.class);
        String channelCcsid = inMessage.getHeader(CICS_CHANNEL_CCSID_HEADER, String.class);
        Short eciRequestTimeout = inMessage.getHeader(CICS_ECI_REQUEST_TIMEOUT_HEADER, Short.class);
        String server = Optional.ofNullable(inMessage.getHeader(CICS_SERVER_HEADER, String.class)).orElse(configuration.getServer());
        String username = Optional.ofNullable(inMessage.getHeader(CICS_USER_ID_HEADER, String.class)).orElse(configuration.getUserId());
        String password = Optional.ofNullable(inMessage.getHeader(CICS_PASSWORD_HEADER, String.class)).orElse(configuration.getPassword());
        int luw = Optional.ofNullable(inMessage.getHeader(CICS_LUW_TOKEN_HEADER, Integer.class)).orElse(ECI_LUW_NEW);
        int extended = Optional.ofNullable(inMessage.getHeader(CICS_EXTEND_MODE_HEADER, Integer.class)).orElse(ECI_NO_EXTEND);

        // Input Data from Exchange
        Channel requestChannel = new Channel(channelName);

        if(isNumber(channelCcsid)) {
            requestChannel.setCCSID(parseInt(channelCcsid));
        }

        createContainers(exchange, requestChannel);

        ECIRequest request =  new ECIRequest(
                ECI_SYNC,                    //ECI call type
                server,                      //CICS server
                username,                    //CICS username
                password,                    //CICS password
                programName,                 //Program to run
                transactionId,               //Transaction to run
                requestChannel,              //Channel
                extended,                    //ECI extend mode
                luw                          //ECI LUW token
        );

        if(eciRequestTimeout != null) {
            request.setECITimeout(eciRequestTimeout);
        }

        if(transactionId != null){
            request.Transid = transactionId;
        }

        return request;

    }


    @Override
    public void toExchange(ECIRequest request, Exchange exchange, int iRc, CICSConfiguration configuration) throws Exception {
        Message message = exchange.getMessage();
        setResponseHeaders(message, request);

        if (!request.hasChannel()) {
            LOGGER.debug("TxId: {} Program Name: {}  did not return a channel. Return code number: {} Return code String: {}",
                    request.Transid, request.Program, request.getCicsRc(), request.getRcString());
            return;
        }

        Map<String, Object> containers = new HashMap<>();

        for (Container cont : request.getChannel().getContainers()) {
            switch (cont.getType()) {
                case CHAR -> {
                    containers.put(cont.getName(), cont.getCHARData());
                }
                case BIT -> {
                    containers.put(cont.getName(), cont.getBITData());
                }
            }
        }
        message.setBody(containers);
    }
}
