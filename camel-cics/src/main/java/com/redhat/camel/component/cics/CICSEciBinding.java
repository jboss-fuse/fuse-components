package com.redhat.camel.component.cics;

import com.ibm.ctg.client.ECIRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

public interface CICSEciBinding {

    ECIRequest toECIRequest(Exchange exchange, CICSConfiguration configuration) throws Exception;

    void toExchange(ECIRequest request, Exchange exchange, int iRc, CICSConfiguration configuration) throws Exception;

    default void setResponseHeaders(Message message, ECIRequest request){
        message.setHeader(CICSConstants.CICS_RETURN_CODE_HEADER, request.getCicsRc());
        message.setHeader(CICSConstants.CICS_RETURN_CODE_STRING_HEADER, request.getCicsRcString());
        message.setHeader(CICSConstants.CICS_LUW_TOKEN_HEADER, request.Luw_Token);
        message.setHeader(CICSConstants.CICS_EXTEND_MODE_HEADER, request.Extend_Mode);
    }

}
