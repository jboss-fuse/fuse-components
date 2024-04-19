/**
 * Copyright 2014 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.redhat.camel.component.cics;

import com.ibm.ctg.client.JavaGateway;
import com.redhat.camel.component.cics.binding.CICSChannelEciBinding;
import com.redhat.camel.component.cics.binding.CICSCommAreaEciBinding;
import com.redhat.camel.component.cics.pool.CICSGatewayFactory;
import com.redhat.camel.component.cics.pool.CICSSingleGatewayFactory;
import com.redhat.camel.component.cics.support.CICSDataExchangeType;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_ECI_TIMEOUT;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_ENCODING;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_INTERFACE_TYPE;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_SERVER_HOST;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_SERVER_PORT;
import static com.redhat.camel.component.cics.CICSConstants.CICS_DEFAULT_SOCKET_TIMEOUT;
import static com.redhat.camel.component.cics.CICSConstants.CICS_ECI_INTERFACE_TYPE;
import static com.redhat.camel.component.cics.CICSConstants.GW_PROTOCOL_TCP;
import static com.redhat.camel.component.cics.support.CICSDataExchangeType.CHANNEL;
import static com.redhat.camel.component.cics.support.CICSDataExchangeType.COMMAREA;


/**
 * Component configuration for CICS component.
 */
@UriParams
public class CICSConfiguration implements Cloneable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CICSConfiguration.class);

    @UriPath(description = "The interface type, can be eci, esi or epi. at the moment only eci is supported.", defaultValue = CICS_DEFAULT_INTERFACE_TYPE + "")
    @Metadata(required = true)
    private String interfaceType = CICS_DEFAULT_INTERFACE_TYPE;

    @UriParam(description = "The address of the CICS Transaction Gateway that this instance connects to", defaultValue = CICS_DEFAULT_SERVER_HOST)
    @Metadata(required = true)
    private String host = CICS_DEFAULT_SERVER_HOST;

    @UriParam(description = "The port of the CICS Transaction Gateway that this instance connects to.", defaultValue = CICS_DEFAULT_SERVER_PORT + "")
    @Metadata(required = true)
    private int port = CICS_DEFAULT_SERVER_PORT;

    @UriParam(description = "The address of the CICS server that this instance connects to")
    @Metadata(required = true)
    private String server;

    @UriParam(description = "User ID to use for authentication", label = "security", secret = true)
    private String userId;

    @UriParam(description = "Password to use for authentication", label = "security", secret = true)
    private String password;

    @UriParam(description = "The password for the encrypted key ring class or keystore", label = "advanced, security", secret = true)
    @Metadata
    private String sslPassword;

    @UriParam(description = "The full classname of the SSL key ring class or keystore file to be used for the client encrypted connection", label = "advanced, security")
    private String sslKeyring;

    @UriParam(description = "Enable debug mode on the underlying IBM CGT client.", defaultValue = "false")
    private Boolean ctgDebug = Boolean.FALSE;

    @Metadata
    @UriParam(description = "The transfer encoding of the message.", defaultValue = CICS_DEFAULT_ENCODING)
    private String encoding = CICS_DEFAULT_ENCODING; // "Cp285";

    @UriParam(description = "The socket connection timeout", label = "advanced", defaultValue = CICS_DEFAULT_SOCKET_TIMEOUT + "")
    private int socketConnectionTimeout;

    @Metadata
    @UriParam(defaultValue = CICS_DEFAULT_ECI_TIMEOUT + "",
            description = "The ECI timeout value associated with this ECIRequest object. An ECI timeout value of zero indicates that "
                    + "this ECIRequest will not be timed out by CICS Transaction Gateway. An ECI timeout value greater than zero "
                    + "indicates that the ECIRequest may be timed out by CICS Transaction Gateway. ECI timeout can expire before "
                    + "a response is received from CICS. This means that the client does not receive the confirmation from CICS "
                    + "that a unit of work has been backed out or committed."
    )
    private short eciTimeout;

    @Metadata
    @UriParam(defaultValue = GW_PROTOCOL_TCP, description = "the protocol that this component will use to connect to the CICS Transaction Gateway.")
    private String protocol;

    @Metadata
    @UriParam(description = "The connection factory to be use")
    private CICSGatewayFactory gatewayFactory;

    @UriParam(description = "The Binding instance to transform a Camel Exchange to EciRequest and vice versa")
    private CICSEciBinding eciBinding;

    @UriPath(defaultValue = "commarea", description = "Use channel instead of commarea data structure")
    private CICSDataExchangeType dataExchangeType = COMMAREA;

    public CICSEciBinding getOrCreateEciBinding() {
        if (this.eciBinding == null) {
            if(dataExchangeType == CHANNEL) {
                this.eciBinding = new CICSChannelEciBinding();
            }else {
                this.eciBinding = new CICSCommAreaEciBinding();
            }
        }
        return this.eciBinding;
    }

    public CICSEciBinding getEciBinding() {
        return getOrCreateEciBinding();
    }

    public void setEciBinding(CICSEciBinding eciBinding) {
        this.eciBinding = eciBinding;
    }

    public short getEciTimeout() {
        return eciTimeout;
    }

    public void setEciTimeout(short eciTimeout) {
        this.eciTimeout = eciTimeout;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public CICSGatewayFactory getOrCreateGatewayFactory() {
        if (this.gatewayFactory == null) {
            CICSSingleGatewayFactory newGatewayFactory = new CICSSingleGatewayFactory();
            newGatewayFactory.setProtocol(GW_PROTOCOL_TCP);
            newGatewayFactory.setHost(host);
            newGatewayFactory.setPort(port);
            newGatewayFactory.setInitialFlow(false);
            newGatewayFactory.setSocketConnectionTimeout(socketConnectionTimeout);
            Properties properties = new Properties();

            if (sslKeyring != null) {
                properties.setProperty(JavaGateway.SSL_PROP_KEYRING_CLASS, sslKeyring);
                if (sslPassword != null) {
                    properties.setProperty(JavaGateway.SSL_PROP_KEYRING_PW, sslPassword);
                }
            }
            newGatewayFactory.setProtocolProperties(properties);
            setGatewayFactory(newGatewayFactory);
        }
        return this.gatewayFactory;
    }

    public CICSGatewayFactory getGatewayFactory() {
        return gatewayFactory;
    }

    public void setGatewayFactory(CICSGatewayFactory gatewayFactory) {
        this.gatewayFactory = gatewayFactory;
    }

    /**
     * Other parameters
     */
    private Map<String, Object> parameters;


    public CICSConfiguration() {
    }

    protected void parseURI(String remaining) {
        String interfaceType;
        String[] split = remaining.split("/");
        if (split.length > 0) {
            interfaceType = split[0];
            if (interfaceType != null && !interfaceType.trim().equalsIgnoreCase(CICS_ECI_INTERFACE_TYPE)){
                throw new IllegalArgumentException("Interface "+interfaceType+" not supported");
            } else{
                this.interfaceType = interfaceType;
            }
        } else {
            throw new IllegalArgumentException("");
        }
        if (split.length > 1) {
            this.dataExchangeType = CICSDataExchangeType.fromValue(split[1]);
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

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public CICSDataExchangeType getDataExchangeType() {
        return dataExchangeType;
    }

    public void setDataExchangeType(CICSDataExchangeType dataExchangeType) {
        this.dataExchangeType = dataExchangeType;
    }
}