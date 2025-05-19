package com.redhat.camel.component.cics.pool;

import com.ibm.ctg.client.JavaGateway;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;


public class CICSSingleGatewayFactory implements CICSGatewayFactory {

    private String protocol = "tcp";
    private String host;
    private int port = 0;
    private boolean initialFlow;
    private String sslKeyring;
    private String sslPassword;
    private int socketConnectionTimeout;
    private Properties protocolProperties;

    public CICSSingleGatewayFactory(){
    }

    public CICSSingleGatewayFactory(String protocol, String host, int port, boolean initialFlow, String sslKeyring, String sslPassword, int socketConnectionTimeout, Properties protocolProperties) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.initialFlow = initialFlow;
        this.sslKeyring = sslKeyring;
        this.sslPassword = sslPassword;
        this.socketConnectionTimeout = socketConnectionTimeout;
        this.protocolProperties = protocolProperties;
    }

    public CICSGateway createGateway() throws IOException {
        CICSGateway gateway = new CICSGateway();
        gateway.setProtocol(protocol);
        gateway.setAddress(host);
        gateway.setPort(port);
        gateway.setInitialFlow(initialFlow);
        gateway.setSocketConnectTimeout(socketConnectionTimeout);

        Properties props = Optional.ofNullable(protocolProperties).orElse(new Properties());

        if (sslKeyring != null) {
            props.setProperty(JavaGateway.SSL_PROP_KEYRING_CLASS, sslKeyring);
            if(sslPassword != null) {
                props.setProperty(JavaGateway.SSL_PROP_KEYRING_PW, sslPassword);
            }
        }
        gateway.setProtocolProperties(props);
        return gateway;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
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

    public boolean isInitialFlow() {
        return initialFlow;
    }

    public void setInitialFlow(boolean initialFlow) {
        this.initialFlow = initialFlow;
    }

    public String getSslKeyring() {
        return sslKeyring;
    }

    public void setSslKeyring(String sslKeyring) {
        this.sslKeyring = sslKeyring;
    }

    public String getSslPassword() {
        return sslPassword;
    }

    public void setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
    }

    public int getSocketConnectionTimeout() {
        return socketConnectionTimeout;
    }

    public void setSocketConnectionTimeout(int socketConnectionTimeout) {
        this.socketConnectionTimeout = socketConnectionTimeout;
    }

    public Properties getProtocolProperties() {
        return protocolProperties;
    }

    public void setProtocolProperties(Properties protocolProperties) {
        this.protocolProperties = protocolProperties;
    }
}
