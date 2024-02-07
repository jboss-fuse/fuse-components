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

public interface CICSConstants {

    int    CICS_DEFAULT_SERVER_PORT       = 2006;
    String CICS_DEFAULT_ENCODING          = "Cp1145";
    int    CICS_DEFAULT_SOCKET_TIMEOUT    = 0;
    short CICS_DEFAULT_ECI_TIMEOUT        = 0;
    String CICS_RETURN_CODE_HEADER        = "CICS_RETURN_CODE";
    String CICS_RETURN_CODE_STRING_HEADER = "CICS_RETURN_CODE_STRING";

    String CICS_RC_HEADER        = "CICS_RC";
    String CICS_RC_STRING_HEADER = "CICS_RC_STRING";



}
