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


import org.eclipse.emf.ecore.xmi.impl.ConfigurationCache;
import org.eclipse.emf.ecore.xmi.impl.XMLString;

class SapConfigurationCache extends ConfigurationCache {

    static final SapConfigurationCache INSTANCE = new SapConfigurationCache();

    private SapConfigurationCache() {
        super();
    }

    @Override
    protected synchronized XMLString getPrinter() {
        if (freePrinterIndex < 0) {
            return new SapXmlString();
        }
        XMLString printer = printers[freePrinterIndex];
        printers[freePrinterIndex--] = null;
        return printer;
    }
}
