package com.redhat.camel.component.cics;

import com.ibm.ctg.client.ECIRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;

import static com.redhat.camel.component.cics.CICSConstants.CICS_ABEND_CODE_HEADER;
import static com.redhat.camel.component.cics.support.CICSAbendCodes.AEY7;

public interface CICSEciBinding {

    ECIRequest toECIRequest(Exchange exchange, CICSConfiguration configuration) throws Exception;

    void toExchange(ECIRequest request, Exchange exchange, int iRc, CICSConfiguration configuration) throws Exception;

    default void setResponseHeaders(Message message, ECIRequest request){
        message.setHeader(CICSConstants.CICS_RETURN_CODE_HEADER, request.getCicsRc());
        message.setHeader(CICSConstants.CICS_RETURN_CODE_STRING_HEADER, request.getCicsRcString());
        message.setHeader(CICSConstants.CICS_LUW_TOKEN_HEADER, request.Luw_Token);
        message.setHeader(CICSConstants.CICS_EXTEND_MODE_HEADER, request.Extend_Mode);
    }

    default void handleTransactionFailed(Message message, ECIRequest request, int iRc, Logger log){
        message.setBody(null);
        message.setHeader(CICS_ABEND_CODE_HEADER, request.Abend_Code);
        if (request.getCicsRc() == ECIRequest.ECI_ERR_SECURITY_ERROR || (request.Abend_Code != null && request.Abend_Code.equalsIgnoreCase(AEY7))) {
            log.debug("Security Flow Exception. Server is unable to validate user ID or password");
        } else if (request.getCicsRc() == ECIRequest.ECI_ERR_TRANSACTION_ABEND) {
            log.debug("Program Flow Exception. An error was returned from the server. Refer to the abend code for further details. '{}'", request.Abend_Code);
        } else {
            log.debug("Unknown Flow Exception. Return code number: {}. Return code String: {}", iRc, request.getCicsRcString());
        }

        log.debug("CicsCodes: {}-{}", request.getCicsRc(), request.getCicsRcString());
        log.debug("RcCodes: {}-{}", request.getRc(), request.getRcString());
    }
}
