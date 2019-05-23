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

import org.eclipse.emf.ecore.xmi.impl.XMLString;

class SapXmlString extends XMLString {

    SapXmlString() {
        super();
    }

    SapXmlString(int lineWidth, String publicId, String systemId, String temporaryFileName) {
        super(lineWidth, publicId, systemId, temporaryFileName);
    }

    @Override
    public void addAttribute(String name, String value)
    {
        final String validName = isAttributeNameInvalid(name) ? prefixAttributeWithUnderscore(name) : name;
        super.addAttribute(validName, value);
    }

    @Override
    public void addAttributeNS(String prefix, String localName, String value) {
        final String validName = isAttributeNameInvalid(localName) ? prefixAttributeWithUnderscore(localName) : localName;
        super.addAttributeNS(prefix, validName, value);
    }

    @Override
    public void startAttribute(final String name) {
        final String validName = isAttributeNameInvalid(name) ? prefixAttributeWithUnderscore(name) : name ;
        super.startAttribute(validName);
    }


    private String prefixAttributeWithUnderscore(String name) {
        return '_' + name;
    }

    /**
     * Returns true if the attribute name starts with a digit or an '_'.
     *
     * @param name attribute name
     * @return true if the attribute name starts with a digit or an '_'.
     */
    private boolean isAttributeNameInvalid(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        char firstChar = name.charAt(0);
        return Character.isDigit(firstChar) || firstChar == '_';
    }
}
