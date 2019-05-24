/**
 * Copyright 2019 Red Hat, Inc.
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
package org.fusesource.camel.component.sap.util;

import java.io.File;
import org.eclipse.emf.ecore.EObject;
import org.fusesource.camel.component.sap.model.rfc.Request;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RfcUtilInvalidXmlTest {

    @Test
    public void testUnmarshallInvalidXml() throws Exception {
        // Load base and derived RFC packages in global registry from test file.
        File file = new File("data/testRfcRegistryInvalidXml.ecore");
        Util.loadRegistry(file);
        String xml = "<?xml version=\"1.0\" encoding=\"ASCII\"?>\n"
                + "<BAPI_FLCONN_GETDETAIL:Request xmlns:BAPI_FLCONN_GETDETAIL=\"http://sap.fusesource.org/rfc/NPL/BAPI_FLCONN_GETDETAIL\" _9CONNECTIONNUMBER=\"5\"/>";
        Structure structure = (Structure) Util.unmarshalXml(xml);
        assertEquals("5", structure.get("9CONNECTIONNUMBER"));
    }

    @Test
    public void testMarshallUnmarhsallInvalidXml() throws Exception {
        // Load base and derived RFC packages in global registry from test file.
        File file = new File("data/testRfcRegistryInvalidXml.ecore");
        Util.loadRegistry(file);

        // Test create request
        Request request = RfcUtil.getRequest("NPL", "BAPI_FLCONN_GETDETAIL");
        request.put("9CONNECTIONNUMBER", "5");
        String xml = Util.marshalXml(request);
        EObject unmarshalXml = Util.unmarshalXml(xml);
        assertEquals(request.get("9CONNECTIONNUMBER"), ((Request)unmarshalXml).get("9CONNECTIONNUMBER"));
    }
}
