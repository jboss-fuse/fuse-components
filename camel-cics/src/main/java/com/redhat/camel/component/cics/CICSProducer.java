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

import com.ibm.ctg.client.ECIRequest;
import com.redhat.camel.component.cics.pool.CICSGateway;
import com.redhat.camel.component.cics.pool.CICSGatewayFactory;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CICS Producer
 *
 * @author Sergio Gutierrez (sgutierr@redhat.com)
 * @author Jose Roman Martin Gil (rmarting@redhat.com)
 * @author Luigi De Masi (ldemasi@redhat.com)
 *
 */
public class CICSProducer extends DefaultProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CICSProducer.class);

    private CICSConfiguration configuration;

    /**
     * @param endpoint
     */
    public CICSProducer(CICSEndpoint endpoint, CICSConfiguration configuration) {
        super(endpoint);
        this.configuration = configuration;
        LOGGER.debug("New CICS Producer");
    }

    /**
     * Process the exchange
     *
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        switch (configuration.getInterfaceType().toUpperCase()) {
            case "ECI":
                processECI(exchange);
                break;
            case "EPI":
                throw new UnsupportedOperationException("EPI is not supported");
            case "ESI":
                throw new UnsupportedOperationException("ESI is not supported");
            default:
                throw new UnsupportedOperationException(configuration.getInterfaceType() + " is not supported");
        }
    }

    public void processECI(Exchange exchange) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Processing Exchange {}", exchange);
        }

        ECIRequest request = configuration.getOrCreateEciBinding().toECIRequest(exchange, configuration);
        request.Commarea_Length = 32768;
        CICSGatewayFactory gf = configuration.getOrCreateGatewayFactory();
        try (CICSGateway gw = gf.createGateway()) {
            gw.open();
            int iRc = gw.flow(request);
            configuration.getOrCreateEciBinding().toExchange(request, exchange, iRc, configuration);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Procesed Exchange {}", exchange);
            }
        }
    }

    @Override
    public CICSEndpoint getEndpoint() {
        return (CICSEndpoint) super.getEndpoint();
    }
}