package com.redhat.camel.component.cics;

import com.redhat.camel.component.cics.pool.CICSGateway;
import com.redhat.camel.component.cics.pool.CICSGatewayPool;
import com.redhat.camel.component.cics.pool.CICSPooledGatewayFactory;
import com.redhat.camel.component.cics.pool.CICSSingleGatewayFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GatewayPoolTest {

    @Test
    public void brokenGatewayShouldBeEvicted() throws Exception {
        String expectedMessage = "Returned object not currently part of this pool";

        CICSSingleGatewayFactory factory = new CICSSingleGatewayFactory();
        factory.setHost("localhost");
        factory.setPort(2345);
        CICSPooledGatewayFactory pooledFactory = new CICSPooledGatewayFactory(factory);
        pooledFactory.setInitialPoolSize(5);
        pooledFactory.setJmxEnabled(true);
        pooledFactory.setMaxTotal(10);
        pooledFactory.start();
        CICSGateway gateway = pooledFactory.createGateway();
        try {
            gateway.open();
        }catch (IOException e){
            //ignore
        }
        assertTrue(gateway.isBroken());

        gateway.close();
        Field privateField = CICSPooledGatewayFactory.class.getDeclaredField("gatewayPool");
        privateField.setAccessible(true);

        CICSGatewayPool pool = (CICSGatewayPool) privateField.get(pooledFactory);

        IllegalStateException thrown = assertThrows(IllegalStateException.class,
               () -> pool.returnObject(gateway), "Expected java.lang.IllegalStateException"
        );
        assertEquals(expectedMessage,thrown.getMessage());
    }
}
