/**
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package com.redhat.camel.component.cics;

import org.apache.camel.spi.Metadata;

public interface CICSConstants {

    int    CICS_DEFAULT_SERVER_PORT       = 2006;
    String CICS_DEFAULT_SERVER_HOST       = "localhost";
    String CICS_DEFAULT_ENCODING          = "Cp1145";
    int    CICS_DEFAULT_SOCKET_TIMEOUT    = 0;
    short CICS_DEFAULT_ECI_TIMEOUT        = 0;

    @Metadata(label = "producer", description = "Return code from this flow operation", javaType = "int")
    String CICS_RETURN_CODE_HEADER = "CICS_RETURN_CODE";

    @Metadata(label = "producer", description = "The CICS return code as a String. The String is the name of the appropriate Java constant, for example, if this header is ECI_NO_ERROR, then the String returned will be \"ECI_NO_ERROR\". If this header is unknown then the String returned will be \"ECI_UNKNOWN_CICS_RC\".\n"
            +"NOTE: for CICS return codes that may have more than one meaning the String returned is a concatenation of the return codes. The only concatenated String is: ECI_ERR_REQUEST_TIMEOUT_OR_ERR_NO_REPLY.", javaType = "java.ang.String")
    String CICS_RETURN_CODE_STRING_HEADER = "CICS_RETURN_CODE_STRING";

    @Metadata(label = "producer", description = "The type of the object in the Camel body that will be used as commarea in the Eci request. " +
            "Possible values are CICS_REQUEST_BODY_STRING or CICS_REQUEST_BODY_BYTE", applicableFor={"commarea"},  javaType = "java.lang.String")
    String CICS_REQUEST_BODY_TYPE_HEADER = "CICS_REQUEST_BODY_TYPE";

    @Metadata(label = "producer", description = "Extend mode of request. The default value is ECI_NO_EXTEND", javaType = "int")
    String CICS_EXTEND_MODE_HEADER = "CICS_EXTEND_MODE";
    @Metadata(label = "producer", description = "Extended Logical Unit of Work token. The default value is ECI_LUW_NEW", javaType = "int")
    String CICS_LUW_TOKEN_HEADER = "CICS_LUW_TOKEN";

    @Metadata(label = "producer", description = "Program to invoke on CICS server.", javaType = "java.lang.String")
    String CICS_PROGRAM_NAME_HEADER = "CICS_PROGRAM_NAME";

    @Metadata(label = "producer", description = "Transaction ID to run CICS program under.", javaType = "java.lang.String")
    String CICS_TRANSACTION_ID_HEADER = "CICS_TRANSACTION_ID";

    @Metadata(label = "producer", description = "Length of COMMAREA. The default value is 0.", javaType = "int")
    String CICS_COMM_AREA_SIZE_HEADER = "CICS_COMM_AREA_SIZE";
    @Metadata(label = "producer", description = "The name of the channel to create", javaType = "java.lang.String")
    String CICS_CHANNEL_NAME_HEADER = "CICS_CHANNEL_NAME";

    @Metadata(label = "producer", description = "The name of the container to create.", javaType = "java.lang.String")
    String CICS_CONTAINER_NAME_HEADER = "CICS_CONTAINER_NAME";

    @Metadata(label = "producer", description = "The CCSID the channel should set as its default.", javaType = "int")
    String CICS_CHANNEL_CCSID_HEADER = "CICS_CHANNEL_CCSID";

    @Metadata(label = "producer", description = "CICS server to direct request to. This header over. This header overrides the value configured in the endpoint.", javaType = "java.lang.String")
    String CICS_SERVER_HEADER = "CICS_SERVER";

    @Metadata(label = "producer", description = "User ID for CICS server. This header overrides the value configured in the endpoint.", secret = true,  javaType = "java.lang.String")
     String CICS_USER_ID_HEADER = "CICS_USER_ID";

    @Metadata(label = "producer", description = "Password or password phrase for CICS server. This header overrides the value configured in the endpoint.", secret = true, javaType = "java.lang.String")
    String CICS_PASSWORD_HEADER = "CICS_PASSWORD";

    @Metadata(label = "producer", description = "CICS transaction abend code.", secret = true, javaType = "java.lang.String")
    String CICS_ABEND_CODE_HEADER = "CICS_ABEND_CODE";

    @Metadata(label = "producer", description = "The value, in seconds, of the ECI timeout for the current ECIRequest. A value of zero indicates that this ECIRequest will not be timed out by CICS Transaction Gateway", defaultValue = "0", javaType = "short")
    String CICS_ECI_REQUEST_TIMEOUT_HEADER = "CICS_ECI_REQUEST_TIMEOUT";


    /** TCP Protocol */
    String GW_PROTOCOL_TCP = "tcp";
    // INTERFACES
    String CICS_ECI_INTERFACE_TYPE = "eci";
    String CICS_DEFAULT_INTERFACE_TYPE = CICS_ECI_INTERFACE_TYPE;
    String CICS_REQUEST_BODY_TYPE_STRING = "CICS_REQUEST_BODY_STRING";
    String CICS_REQUEST_BODY_TYPE_BYTE = "CICS_REQUEST_BODY_BYTE";

}
