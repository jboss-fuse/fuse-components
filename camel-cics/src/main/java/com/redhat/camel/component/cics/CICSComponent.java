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

import com.redhat.camel.component.cics.pool.CICSGatewayFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;
import org.apache.camel.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * CICS component to invoke programs running on CICS Transaction Gateway systems.
 *
 * @author Sergio Gutierrez (sgutierr@redhat.com)
 * @author Jose Roman Martin Gil (rmarting@redhat.com)
 * @author Luigi De Masi (ldemasi@redhat.com)
 */
@Component("cics")
public class CICSComponent extends DefaultComponent {

    @Metadata(label = "advanced", description = "To use a shared CICS configuration", autowired = true)
    private CICSConfiguration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(CICSComponent.class);

    public CICSComponent() {
        this(null);
    }

    public CICSComponent(CamelContext context) {
        super(context);
        this.configuration = new CICSConfiguration();
    }

    /**
     * @return New instance of <code>CICSEndpoint</code>
     *
     * @see DefaultComponent#createEndpoint(java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        LOGGER.info("Creating CICS Endpoint with uri: {}", URISupport.sanitizeUri(uri));

        CICSConfiguration config;
        if (configuration != null) {
            config = configuration.copy();
        } else {
            config = new CICSConfiguration();
        }

        setProperties(config, parameters);
        config.parseURI(remaining);
        CICSEndpoint cicsEndpoint = new CICSEndpoint(uri, this, config);
        return cicsEndpoint;
    }

    public CICSConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the CICS configuration
     *
     * @param configuration the configuration to use by default for endpoints
     */
    public void setConfiguration(CICSConfiguration configuration) {
        this.configuration = configuration.copy();
    }


    @Override
    protected void doInit() throws Exception {
        if (configuration.getGatewayFactory() == null){
            Set<CICSGatewayFactory> beans = getCamelContext().getRegistry().findByType(CICSGatewayFactory.class);
            if (beans.size() == 1) {
                CICSGatewayFactory gf = beans.iterator().next();
                configuration.setGatewayFactory(gf);
            } else if (beans.size() > 1 ) {
                LOGGER.debug("Cannot autowire CICSGatewayFactory as {} instances found in registry.", beans.size());
            }
        }
        super.doInit();
    }

}