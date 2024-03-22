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

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.support.DefaultProducer;
import com.redhat.camel.component.cics.adapter.CICSAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CICS Producer
 * 
 * @author Sergio Gutierrez (sgutierr@redhat.com)
 * @author Jose Roman Martin Gil (rmarting@redhat.com)
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

        LOGGER.info("New CICS Producer");
    }

    /**
     * Process the exchange
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Processing Exchange {}", exchange);
        }

        processCTGProcedure(exchange);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Procesed Exchange {}", exchange);
        }
    }

    /**
     * @param exchange
     * 
     * @throws Exception
     */
    private void processCTGProcedure(Exchange exchange) throws Exception {
        // All Headers
        Message inMessage = exchange.getIn();
        // Program Headers: programName, transactionID and commAreaSize
        String programName   = inMessage.getHeader("programName",   String.class);
        String transactionId = inMessage.getHeader("transactionId", String.class);
        Integer commAreaSize = Optional.ofNullable(inMessage.getHeader("commAreaSize", Integer.class)).orElse(-1);



        // Input CommArea Data from Exchange
        Object commArea = inMessage.getBody();
        // Output CommArea Data to include in Exchange
        Object result = null;

        // CICS Adapter
        CICSAdapter cicsAdapter = null;

        try {
            // Initialize CICS adapter
            cicsAdapter = new CICSAdapter(configuration);

            // Open Gateway
            cicsAdapter.open();

            // Preparing CommArea Data
            if (commArea instanceof String) {
                // Execute Transaction with String
                result = cicsAdapter.runTransaction(programName, transactionId, (String) commArea, commAreaSize,exchange);
            } else if (commArea instanceof byte[]) {
                // Execute Transaction with byte[]
                result = cicsAdapter.runTransaction(programName, transactionId, (byte[]) commArea, exchange);
            } else {
                byte[] abCommArea = null;
                if (commAreaSize > 0) {
                    abCommArea = new byte[commAreaSize];
                } else {
                    abCommArea = new byte[0];
                }

                LOGGER.warn("Run Transaction with data format not available. Defining Default CommArea with size: {}", abCommArea.length);

                // Execute Transaction with byte[]
                result = cicsAdapter.runTransaction(programName, transactionId, abCommArea, exchange);
            }

            // Set Output Results in Exchange
            // copy headers
            exchange.getMessage().setHeaders(inMessage.getHeaders());
            // producer returns a single response
            exchange.getMessage().setBody(result);
        } catch (Exception e) {
            LOGGER.error("Exception in process Exchange. Message: " + e.getMessage(), e);

            // Setting exception in exchange
            exchange.setException(e);
        } finally {
            // Close all connections
            cicsAdapter.close();
        }
    }

    @Override
    public CICSEndpoint getEndpoint() {
        return (CICSEndpoint) super.getEndpoint();
    }

}