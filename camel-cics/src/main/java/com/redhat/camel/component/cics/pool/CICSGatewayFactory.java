package com.redhat.camel.component.cics.pool;

import java.util.Properties;

public interface CICSGatewayFactory {

    CICSGateway createGateway() throws Exception;

    String getProtocol();

    void setProtocol(String protocol);

    String getHost();

    void setHost(String host);

    int getPort();

    void setPort(int port);

    boolean isInitialFlow();

    void setInitialFlow(boolean initialFlow);

    String getSslKeyring();

    void setSslKeyring(String sslKeyring);

    String getSslPassword();

    void setSslPassword(String sslPassword);

    int getSocketConnectionTimeout();

    void setSocketConnectionTimeout(int socketConnectionTimeout);

    Properties getProtocolProperties();

    void setProtocolProperties(Properties protocolProperties);
}
