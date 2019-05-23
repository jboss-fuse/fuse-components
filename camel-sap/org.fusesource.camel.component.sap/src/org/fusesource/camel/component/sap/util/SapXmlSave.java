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
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.NameInfoImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

class SapXmlSave extends XMLSaveImpl {

    private static final int MAX_UTF_MAPPABLE_CODEPOINT = 0x10FFFF;
    private static final int MAX_LATIN1_MAPPABLE_CODEPOINT = 0xFF;
    private static final int MAX_ASCII_MAPPABLE_CODEPOINT = 0x7F;

    SapXmlSave(XMLHelper helper) {
        super(helper);
    }

    @Override
    protected void saveElementID(EObject o) {
        Util.addNameSpaceDeclarations(o,doc);
        super.saveElementID(o);
    }

    @Override
    protected void init(XMLResource resource, Map<?, ?> options) {
        useCache =  Boolean.TRUE.equals(options.get(XMLResource.OPTION_CONFIGURATION_CACHE));

        nameInfo = new NameInfoImpl();
        declareXSI = false;
        keepDefaults = Boolean.TRUE.equals(options.get(XMLResource.OPTION_KEEP_DEFAULT_CONTENT));
        useEncodedAttributeStyle = Boolean.TRUE.equals(options.get(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE));
        declareSchemaLocationImplementation = Boolean.TRUE.equals(options.get(XMLResource.OPTION_SCHEMA_LOCATION_IMPLEMENTATION));
        declareSchemaLocation = declareSchemaLocationImplementation || Boolean.TRUE.equals(options.get(XMLResource.OPTION_SCHEMA_LOCATION));

        Object saveTypeInfoOption = options.get(XMLResource.OPTION_SAVE_TYPE_INFORMATION);
        if (saveTypeInfoOption instanceof Boolean)
        {
            saveTypeInfo = saveTypeInfoOption.equals(Boolean.TRUE);
            if (saveTypeInfo)
            {
                xmlTypeInfo =
                        new XMLTypeInfo()
                        {
                            public boolean shouldSaveType(EClass objectType, EClassifier featureType, EStructuralFeature feature)
                            {
                                return objectType != anyType;
                            }

                            public boolean shouldSaveType(EClass objectType, EClass featureType, EStructuralFeature feature)
                            {
                                return true;
                            }
                        };
            }
        }
        else
        {
            saveTypeInfo = saveTypeInfoOption != null;
            if (saveTypeInfo)
            {
                xmlTypeInfo = (XMLTypeInfo)saveTypeInfoOption;
            }
        }

        anyType = (EClass)options.get(XMLResource.OPTION_ANY_TYPE);
        anySimpleType = (EClass)options.get(XMLResource.OPTION_ANY_SIMPLE_TYPE);
        if (anyType == null)
        {
            anyType = XMLTypePackage.eINSTANCE.getAnyType();
            anySimpleType = XMLTypePackage.eINSTANCE.getSimpleAnyType();
        }

        Object extendedMetaDataOption = options.get(XMLResource.OPTION_EXTENDED_META_DATA);
        if (extendedMetaDataOption instanceof Boolean)
        {
            if (extendedMetaDataOption.equals(Boolean.TRUE))
            {
                extendedMetaData =
                        resource == null || resource.getResourceSet() == null ?
                                ExtendedMetaData.INSTANCE :
                                new BasicExtendedMetaData(resource.getResourceSet().getPackageRegistry());
            }
        }
        else
        {
            extendedMetaData = (ExtendedMetaData)options.get(XMLResource.OPTION_EXTENDED_META_DATA);
        }

        // set serialization options
        if (!toDOM)
        {
            declareXML = !Boolean.FALSE.equals(options.get(XMLResource.OPTION_DECLARE_XML));

            if (options.get(XMLResource.OPTION_FLUSH_THRESHOLD) instanceof Integer)
            {
                flushThreshold = (Integer)options.get(XMLResource.OPTION_FLUSH_THRESHOLD);
            }

            String temporaryFileName = null;
            if (Boolean.TRUE.equals(options.get(XMLResource.OPTION_USE_FILE_BUFFER)))
            {
                try
                {
                    temporaryFileName = File.createTempFile("XMLSave", null).getPath();
                }
                catch (IOException exception)
                {
                    // If we can't create a temp file then we have to ignore the option.
                }
            }

            Integer lineWidth = (Integer)options.get(XMLResource.OPTION_LINE_WIDTH);
            int effectiveLineWidth = lineWidth == null ? Integer.MAX_VALUE : lineWidth;
            String publicId = null, systemId = null;
            if (resource != null && Boolean.TRUE.equals(options.get(XMLResource.OPTION_SAVE_DOCTYPE)))
            {
                publicId = resource.getPublicId();
                systemId = resource.getSystemId();
            }
            if (useCache)
            {
                doc = SapConfigurationCache.INSTANCE.getPrinter();
                doc.reset(publicId, systemId, effectiveLineWidth, temporaryFileName);
                escape =null;
            }
            else
            {
                doc = new SapXmlString(effectiveLineWidth, publicId, systemId, temporaryFileName);
                escape = Boolean.TRUE.equals(options.get(XMLResource.OPTION_SKIP_ESCAPE)) ? null : new Escape();
            }

            if (Boolean.FALSE.equals(options.get(XMLResource.OPTION_FORMATTED)))
            {
                doc.setUnformatted(true);
            }


            escapeURI = Boolean.FALSE.equals(options.get(XMLResource.OPTION_SKIP_ESCAPE_URI)) ? escape : null;

            if (options.containsKey(XMLResource.OPTION_ENCODING))
            {
                encoding = (String)options.get(XMLResource.OPTION_ENCODING);
            }
            else if (resource != null)
            {
                encoding = resource.getEncoding();
            }

            if (options.containsKey(XMLResource.OPTION_XML_VERSION))
            {
                xmlVersion = (String)options.get(XMLResource.OPTION_XML_VERSION);
            }
            else if (resource != null)
            {
                xmlVersion = resource.getXMLVersion();
            }

            if (escape != null)
            {
                int maxSafeChar = MAX_UTF_MAPPABLE_CODEPOINT;
                if (encoding != null)
                {
                    if (encoding.equalsIgnoreCase("ASCII") || encoding.equalsIgnoreCase("US-ASCII"))
                    {
                        maxSafeChar = MAX_ASCII_MAPPABLE_CODEPOINT;
                    }
                    else if (encoding.equalsIgnoreCase("ISO-8859-1"))
                    {
                        maxSafeChar = MAX_LATIN1_MAPPABLE_CODEPOINT;
                    }
                }

                escape.setMappingLimit(maxSafeChar);
                if (!"1.0".equals(xmlVersion))
                {
                    escape.setAllowControlCharacters(true);
                }

                escape.setUseCDATA(Boolean.TRUE.equals(options.get(XMLResource.OPTION_ESCAPE_USING_CDATA)));
            }

            resourceEntityHandler = (XMLResource.ResourceEntityHandler)options.get(XMLResource.OPTION_RESOURCE_ENTITY_HANDLER);
            if (resourceEntityHandler instanceof XMLResource.URIHandler && !options.containsKey(XMLResource.OPTION_URI_HANDLER))
            {
                Map<Object, Object> newOptions = new LinkedHashMap<Object, Object>(options);
                newOptions.put(XMLResource.OPTION_URI_HANDLER, resourceEntityHandler);
                options = newOptions;
            }
        }
        processDanglingHREF = (String) options.get(XMLResource.OPTION_PROCESS_DANGLING_HREF);
        helper.setProcessDanglingHREF(processDanglingHREF);

        map = (XMLResource.XMLMap) options.get(XMLResource.OPTION_XML_MAP);
        if (map != null)
        {
            helper.setXMLMap(map);

            if (map.getIDAttributeName() != null)
            {
                idAttributeName = map.getIDAttributeName();
            }
        }

        if (resource != null)
        {
            eObjectToExtensionMap = resource.getEObjectToExtensionMap();
            if (eObjectToExtensionMap.isEmpty())
            {
                eObjectToExtensionMap = null;
            }
            else if (extendedMetaData == null)
            {
                extendedMetaData =
                        resource.getResourceSet() == null ?
                                ExtendedMetaData.INSTANCE :
                                new BasicExtendedMetaData(resource.getResourceSet().getPackageRegistry());
            }
        }

        if (extendedMetaData != null)
        {
            helper.setExtendedMetaData(extendedMetaData);
            if (resource != null && resource.getContents().size() >=1)
            {
                EObject root = resource.getContents().get(0);
                EClass eClass = root.eClass();

                EReference xmlnsPrefixMapFeature = extendedMetaData.getXMLNSPrefixMapFeature(eClass);
                if (xmlnsPrefixMapFeature != null)
                {
                    @SuppressWarnings("unchecked") EMap<String, String> xmlnsPrefixMap = (EMap<String, String>)root.eGet(xmlnsPrefixMapFeature);
                    helper.setPrefixToNamespaceMap(xmlnsPrefixMap);
                }
            }
        }

        elementHandler = (XMLResource.ElementHandler)options.get(XMLResource.OPTION_ELEMENT_HANDLER);

        @SuppressWarnings("unchecked") List<Object> lookup = (List<Object>)options.get(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE);
        if (lookup != null)
        {
            // caching turned on by the user
            if (lookup.isEmpty())
            {
                featureTable = new Lookup(map, extendedMetaData, elementHandler);
                lookup.add(featureTable);
            }
            else
            {
                featureTable = (Lookup)lookup.get(INDEX_LOOKUP);
            }
        }
        else
        {
            //no caching
            featureTable = new Lookup(map, extendedMetaData, elementHandler);
        }

        helper.setOptions(options);

        proxyAttributes = Boolean.TRUE.equals(options.get(XMLResource.OPTION_PROXY_ATTRIBUTES));
    }
}
