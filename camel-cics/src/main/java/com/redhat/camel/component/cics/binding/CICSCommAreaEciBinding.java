package com.redhat.camel.component.cics.binding;

import com.ibm.ctg.client.ECIRequest;
import com.redhat.camel.component.cics.CICSConfiguration;
import com.redhat.camel.component.cics.CICSEciBinding;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static com.ibm.ctg.client.ECIReturnCodes.ECI_NO_ERROR;
import static com.redhat.camel.component.cics.CICSConstants.*;
import static com.redhat.camel.component.cics.support.CICSUtils.getBytes;

public class CICSCommAreaEciBinding implements CICSEciBinding {

    private static final Logger LOGGER = LoggerFactory.getLogger(CICSCommAreaEciBinding.class);

    public ECIRequest toECIRequest(Exchange exchange, CICSConfiguration configuration) throws UnsupportedEncodingException {
        Message inMessage = exchange.getMessage();
        // Program Headers: programName, transactionID and commAreaSize
        int commAreaSize = Optional.ofNullable(inMessage.getHeader(CICS_COMM_AREA_SIZE_HEADER, Integer.class)).orElse(0);
        String programName = inMessage.getHeader(CICS_PROGRAM_NAME_HEADER, String.class);
        String transactionId = inMessage.getHeader(CICS_TRANSACTION_ID_HEADER, String.class);
        Short eciRequestTimeout = inMessage.getHeader(CICS_ECI_REQUEST_TIMEOUT_HEADER, Short.class);
        String server = Optional.ofNullable(inMessage.getHeader(CICS_SERVER_HEADER, String.class)).orElse(configuration.getServer());
        int luw = Optional.ofNullable(inMessage.getHeader(CICS_LUW_TOKEN_HEADER, Integer.class)).orElse(ECIRequest.ECI_LUW_NEW);
        int extended = Optional.ofNullable(inMessage.getHeader(CICS_EXTEND_MODE_HEADER, Integer.class)).orElse(ECIRequest.ECI_NO_EXTEND);
        String encoding = Optional.ofNullable(inMessage.getHeader(CICS_ENCODING_HEADER, String.class)).orElse(configuration.getEncoding());
        int callType = Optional.ofNullable(inMessage.getHeader(CICS_CALL_TYPE_HEADER, Integer.class)).orElse(configuration.getCallType());

        // Input CommArea Data from Exchange
        Object commArea = inMessage.getBody();
        byte[] byteCommArea;

        if (commArea instanceof String) {
            byteCommArea = stringToBytesCommArea((String) commArea, commAreaSize, encoding);
        } else if (commArea instanceof byte[]) {
            byteCommArea = (byte[]) commArea;
        } else {
            LOGGER.warn("Body is not type of String or byte[]");
            if (commAreaSize > 0) {
                byteCommArea = new byte[commAreaSize];
            } else {
                byteCommArea = new byte[0];
            }
            LOGGER.warn("Run Transaction with data format not available. Defining Default CommArea with size: {}", byteCommArea.length);
        }

        ECIRequest request = new ECIRequest(
                callType,
                server, // CICS Server
                configuration.getUserId(), // UserId, null for none
                configuration.getPassword(), // Password, null for none
                programName, // Program name
                transactionId, //transactionId
                byteCommArea, // COMMAREA
                commAreaSize, // COMMAREA SIZE
                luw,
                extended);

        if (eciRequestTimeout != null) {
            request.setECITimeout(eciRequestTimeout);
        }
        return request;
    }


    public void toExchange(ECIRequest request, Exchange exchange, int iRc, CICSConfiguration configuration) throws UnsupportedEncodingException {
        Message message = exchange.getMessage();
        setResponseHeaders(message, request);

        if (iRc == ECI_NO_ERROR) {
            LOGGER.debug("Flow executed successfully");
            LOGGER.trace("Gateway Flow Exception. Return code number:" + iRc + " Return code String: " + request.getRcString());

            message.setBody(request.Commarea);
            return;
        }

        handleTransactionFailed(message, request, iRc,LOGGER);
    }


    private byte[] stringToBytesCommArea(String inputCommArea, int commAreaSize, String encoding) throws UnsupportedEncodingException {
        byte[] byteCommArea;
        if (commAreaSize > 0) {
            byteCommArea = new byte[commAreaSize];
            if (inputCommArea != null) {
                // Calls local getBytes function to extract byte array in either ASCII or unconverted form.
                System.arraycopy(getBytes(inputCommArea, encoding), 0, byteCommArea, 0,
                        Math.min(byteCommArea.length, inputCommArea.length()));
            }
        } else if (inputCommArea != null) {
            // Calls local getBytes function to extract byte array in either ASCII or unconverted form.
            byteCommArea = getBytes(inputCommArea, encoding);
        } else {
            byteCommArea = new byte[commAreaSize];
        }
        LOGGER.trace("Input CommArea String Data:\n-** INPUT COMMAREA **-\n{}\n-** END INPUT COMMAREA **-", inputCommArea);
        return byteCommArea;
    }
}


