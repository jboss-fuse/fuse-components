package com.redhat.camel.component.cics;

import com.redhat.camel.component.cics.pool.CICSSingleGatewayFactory;
import org.apache.camel.spi.Registry;

public class SingleCoonectionFactoryTest extends AbstractCICSTest {

    @Override
    protected void bindToRegistry(Registry registry) throws Exception {
        int  CTG_PORT = ctgContainer.getMappedPort(2006);
        String CTG_HOST =  ctgContainer.getHost();
        CICSSingleGatewayFactory factory = new CICSSingleGatewayFactory();
        factory.setHost(CTG_HOST);
        factory.setPort(CTG_PORT);
        factory.setProtocol("tcp");
        registry.bind("factory", factory);
    }


    @Override
    protected String getOptions(String host, int port) {
        return "?gatewayFactory=#factory";
    }
}
