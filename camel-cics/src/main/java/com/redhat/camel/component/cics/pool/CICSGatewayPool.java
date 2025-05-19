package com.redhat.camel.component.cics.pool;

import com.redhat.camel.component.cics.exceptions.CICSRuntimeException;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class CICSGatewayPool extends GenericObjectPool<CICSGateway> {

    public CICSGatewayPool(PooledObjectFactory<CICSGateway> factory, GenericObjectPoolConfig<CICSGateway> config) {
        super(factory, config);
    }

    public void returnBrokenGateway(final CICSGateway resource) {
        if (resource == null) {
            return;
        }
        try {
            super.invalidateObject(resource);
        } catch (Exception e) {
            throw  new CICSRuntimeException("Could not return the broken resource to the pool", e);
        }
    }
}
