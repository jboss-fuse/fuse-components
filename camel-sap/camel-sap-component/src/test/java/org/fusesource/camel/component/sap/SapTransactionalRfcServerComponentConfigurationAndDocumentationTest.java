/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.camel.component.sap;

import java.net.URI;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.util.URISupport;
import org.junit.Test;

import static org.junit.Assert.*;

public class SapTransactionalRfcServerComponentConfigurationAndDocumentationTest extends CamelTestSupport {

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    public void testComponentConfiguration() throws Exception {
        SapTransactionalRfcServerComponent comp = context.getComponent("sap-trfc-server", SapTransactionalRfcServerComponent.class);

        Endpoint endpoint = comp.createEndpoint("sap-trfc-destination:destinationName:rfcName?stateful=true&transacted=false");
        String fullEndpointUri = endpoint.getEndpointUri();
        URI uri = new URI(fullEndpointUri);
        Map<String, Object> parameters = URISupport.parseParameters(uri);

        assertEquals("true", parameters.get("stateful"));
        assertEquals("false", parameters.get("transacted"));

    }
}
