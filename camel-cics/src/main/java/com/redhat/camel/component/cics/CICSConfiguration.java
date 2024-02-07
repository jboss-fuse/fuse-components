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

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_ECI_TIMEOUT;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_SOCKET_TIMEOUT;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_SERVER_PORT;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_ENCODING;


/**
 * Component configuration for CICS component.
 */
@UriParams
public class CICSConfiguration implements Cloneable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CICSConfiguration.class);

    @UriPath(description = "The address of the CICS Transaction Gateway that this instance connects to")
    @Metadata(required = true)
    private String host;

    @UriPath(description = "The port of the CICS Transaction Gateway that this instance connects to.", defaultValue = CICS_DEFAULT_SERVER_PORT +"")
    @Metadata(required = true)
    private int port = CICS_DEFAULT_SERVER_PORT;

    @UriPath(description = "The address of the CICS Transaction Gateway that this instance connects to")
    @Metadata(required = true)
    private String server;

    @UriParam(description = "User ID to use for authentication", label = "security", secret = true)
    private String userId;

    @UriParam(description = "Password to use for authentication", label = "security", secret = true)
    private String password;

    @UriParam(description = "The password for the encrypted key ring class or keystore", label = "advanced, security")
    @Metadata
    private String sslPassword;

    @UriParam(description = "The full classname of the SSL key ring class or keystore file to be used for the client encrypted connection", label = "advanced, security")
    private String sslKeyring;

    @UriParam(description = "Enable debug mode on the underlying IBM CGT client.",  defaultValue = "false")
    private Boolean ctgDebug = Boolean.FALSE;

    @Metadata
    @UriParam(description = "The transfer encoding of the message.", defaultValue = CICS_DEFAULT_ENCODING)
    private String encoding = CICS_DEFAULT_ENCODING; // "Cp285";

    @UriParam(description = "The socket connection timeout", label = "advanced", defaultValue = CICS_DEFAULT_SOCKET_TIMEOUT +"")
    private int socketConnectionTimeout;

    @Metadata
    @UriParam(defaultValue = CICS_DEFAULT_ECI_TIMEOUT +"",
            description = "The ECI timeout value associated with this ECIRequest object. An ECI timeout value of zero indicates that "
                         + "this ECIRequest will not be timed out by CICS Transaction Gateway. An ECI timeout value greater than zero "
                         + "indicates that the ECIRequest may be timed out by CICS Transaction Gateway. ECI timeout can expire before "
                         + "a response is received from CICS. This means that the client does not receive the confirmation from CICS "
                         + "that a unit of work has been backed out or committed."
    )
    private short eciTimeout;

    public short getEciTimeout() {
        return eciTimeout;
    }

    public void setEciTimeout(short eciTimeout) {
        this.eciTimeout = eciTimeout;
    }



    /**
     * Other parameters
     */
    private Map<String, Object> parameters;


    public CICSConfiguration() {
    }

    protected void parseURI(String remaining){
        String address;
        String[] split = remaining.split("/");
        if (split.length > 0) {
             address = split[0];
        } else {
            throw new IllegalArgumentException();
        }
        if (split.length > 1) {
            this.server= split[1];
        } else {
            throw new IllegalArgumentException();
        }

        String[] addressSplit = address.split(":");
        if (addressSplit.length > 0) {
            this.host = addressSplit[0];
        } else {
            throw new IllegalArgumentException();
        }
        if (addressSplit.length > 1) {
            this.port = Integer.parseInt(addressSplit[1]);
        } else {
            LOGGER.debug("Port not provided. Fallback to default port :" + CICS_DEFAULT_SERVER_PORT);
        }

    }

    public CICSConfiguration copy() {
        try {
            CICSConfiguration copy = (CICSConfiguration) clone();
            // override any properties where a reference copy isn't what we want
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeCamelException(e);
        }
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String serverName) {
        this.server = serverName;
    }

    /**
     * Optional parameters to be used by endpoints
     *
     * @param parameters parameters
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSslPassword() {
        return sslPassword;
    }

    public void setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
    }

    public String getSslKeyring() {
        return sslKeyring;
    }

    public void setSslKeyring(String sslKeyring) {
        this.sslKeyring = sslKeyring;
    }

    public Boolean getCtgDebug() {
        return ctgDebug;
    }


    public void setCtgDebug(Boolean ctgDebugOn) {
        this.ctgDebug = ctgDebugOn;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getSocketConnectionTimeout() {
        return socketConnectionTimeout;
    }

    public void setSocketConnectionTimeout(int socketConnectionTimeout) {
        this.socketConnectionTimeout = socketConnectionTimeout;
    }
}