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
package com.redhat.camel.component.cics.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Sergio Gutierrez (sgutierr@redhat.com)
 * @author Jose Roman Martin Gil (rmarting@redhat.com)
 */
public class ByteExchangeProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteExchangeProcessor.class);
    
    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("Processing Exchange {}", exchange.getIn().getBody());
        
        if (exchange.getIn().getBody() instanceof byte[]) {
            byte[] inBody = (byte[]) exchange.getIn().getBody();
            
            String data = new String(inBody, "Cp1145");
            
            LOGGER.info("String Data in Exchange:\n-** EXCHANGE BODY **-\n{}\n-** END EXCHANGE BODY **-", data);
        }
    }

}