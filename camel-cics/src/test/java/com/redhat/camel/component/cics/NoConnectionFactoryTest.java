package com.redhat.camel.component.cics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoConnectionFactoryTest extends AbstractCICSTest {
    public static final Logger LOG = LoggerFactory.getLogger(NoConnectionFactoryTest.class);

    @Override
    protected String getOptions(String host, int port) {
        return "?host="+host+"&port="+port+"&protocol=tcp";
    }
}
