package com.redhat.camel.component.cics;

import ch.qos.logback.core.read.ListAppender;
import com.ibm.ctg.client.JavaGateway;
import com.redhat.camel.component.cics.pool.CICSGateway;
import com.redhat.camel.component.cics.pool.CICSPooledGatewayFactory;
import com.redhat.camel.component.cics.pool.CICSSingleGatewayFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import org.slf4j.LoggerFactory;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Logger;

import static org.assertj.core.api.Assertions.*;

public class CICSGatwayTest {

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<ILoggingEvent>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(CICSGateway.class)).addAppender(logWatcher);
    }


    @Test
    public void gatewayAlreadyOperedTest() throws Exception{
            CICSSingleGatewayFactory factory = new CICSSingleGatewayFactory();
            factory.setHost("localhost");
            factory.setPort(2222);
            //factory.setSslKeyring(camelCicsSslKeyring);
            //factory.setSslPassword(camelCicsSslPassword);
            factory.setProtocol("tcp");
            CICSPooledGatewayFactory gatewayPool = new CICSPooledGatewayFactory(factory);
            gatewayPool.setMaxTotal(5);
            gatewayPool.setMinIdle(2);
            gatewayPool.setInitialPoolSize(0);
            gatewayPool.setJmxEnabled(true);


            JavaGateway jgw = Mockito.mock(JavaGateway.class);
            Mockito.doNothing().when(jgw).open();
            Mockito.when(jgw.isOpen()).thenReturn(false).thenReturn(true);

            //CICSGateway cg = gatewayPool.createGateway();
            CICSGateway gw = new CICSGateway();
            Field f1 = gw.getClass().getDeclaredField("gateway");
            f1.setAccessible(true);
            f1.set(gw, jgw);
            gw.open();
            int logSize = logWatcher.list.size();
            assertThat(logWatcher.list.get(logSize - 1).getFormattedMessage()).contains("Opening connection");
            gw.open();
            logSize = logWatcher.list.size();
            assertThat(logWatcher.list.get(logSize - 1).getFormattedMessage()).contains("already opened");

    }


    @AfterEach
    void teardown() {
        ((Logger) LoggerFactory.getLogger(CICSGateway.class)).detachAndStopAllAppenders();
    }

}
