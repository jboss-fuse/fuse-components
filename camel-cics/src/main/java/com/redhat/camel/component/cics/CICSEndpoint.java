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

import org.apache.camel.Category;
import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.util.ObjectHelper;
import org.apache.camel.util.URISupport;
import org.apache.camel.util.UnsafeUriCharactersEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CICS Endpoint<br/>
 * 
 * A CICS Endpoint is defined by the next pattern:<br/>
 * 
 * <code>cics://interfaceType[?options]</code>
 * 
 * @author Sergio Gutierrez (sgutierr@redhat.com)
 * @author Jose Roman Martin Gil (rmarting@redhat.com)
 * @author Luigi De Masi (ldemasi@redhat.com)
 */
@UriEndpoint(firstVersion = "4.4-redhat", scheme = "cics", title = "CICS", syntax = "cics://interfaceType/[dataExchangeType][?options]",
        producerOnly = true, headersClass = CICSConstants.class, category = {Category.SAAS})
public class CICSEndpoint extends DefaultEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CICSEndpoint.class);


    @UriParam
    private CICSConfiguration configuration;



    public CICSEndpoint(String endpointUri, CICSComponent component, CICSConfiguration configuration) {
        super(UnsafeUriCharactersEncoder.encode(endpointUri), component);
        LOGGER.info("New CICS Endpoint with endpointUri: {}", URISupport.sanitizeUri(endpointUri));
        this.configuration = configuration;
    }

    /**
     * Constructs a fully-initialized {@link CICSEndpoint} instance
     * for the given endpoint URI.
     *
     * @param endpointUri the full URI used to create this endpoint
     * @param component   the component that created this endpoint
     * @param configuration  the {@link CICSConfiguration} to use
     */

    public CICSEndpoint(String endpointUri, Component component, CICSConfiguration configuration) {
        super(UnsafeUriCharactersEncoder.encode(endpointUri), component);
        LOGGER.info("New CICS Endpoint with endpointUri: {}", URISupport.sanitizeUri(endpointUri));
        this.configuration = configuration;
    }


    /**
     * Creates a new producer which is used send messages into the cics endpoint.
     *
     * @return A new instance of {@link CICSProducer}
     * @throws Exception can be thrown
     * 
     * @see org.apache.camel.Endpoint#createProducer()
     */
    @Override
    public Producer createProducer() throws Exception {
        ObjectHelper.notNull(configuration, "config");
        return new CICSProducer(this, this.configuration);
    }

    /**
     * This component is only for use as a Producer, not as a Consumer
     * 
     * @return <code>null</code>
     * 
     * @see org.apache.camel.Endpoint#createConsumer(org.apache.camel.Processor)
     */
    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException("You cannot receive messages from this endpoint");
    }



    /**
     * This implementation is singleton and this method returns true.
     *
     * @return <code>true</code>
     * @see org.apache.camel.IsSingleton#isSingleton()
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CICSEndpoint [host=");
        builder.append(configuration.getHost());
        builder.append(", port=");
        builder.append(configuration.getPort());
        builder.append(", server=");
        builder.append(configuration.getServer());
        builder.append(", userId=");
        builder.append(configuration.getUserId());
        builder.append(", password=");
        builder.append("* PASSWORD *");
        builder.append(", sslPassword=");
        builder.append("* SSL PASSWORD *");
        builder.append(", sslKeyring=");
        builder.append(configuration.getSslKeyring());
        builder.append(", ctgDebug=");
        builder.append(configuration.getCtgDebug());
        builder.append(", encoding=");
        builder.append(configuration.getEncoding());
        builder.append("]");
        return builder.toString();
    }

    public CICSConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CICSConfiguration configuration) {
        this.configuration = configuration;
    }
}