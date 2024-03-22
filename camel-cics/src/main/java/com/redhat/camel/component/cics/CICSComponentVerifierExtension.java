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

import org.apache.camel.component.extension.verifier.DefaultComponentVerifierExtension;
import org.apache.camel.component.extension.verifier.ResultBuilder;
import org.apache.camel.component.extension.verifier.ResultErrorBuilder;
import org.apache.camel.component.extension.verifier.ResultErrorHelper;
import com.redhat.camel.component.cics.adapter.CICSAdapter;

import java.io.IOException;
import java.util.Map;

public class CICSComponentVerifierExtension extends DefaultComponentVerifierExtension {

    public CICSComponentVerifierExtension() {
        this("cics");
    }
    public CICSComponentVerifierExtension(String scheme) {

        super(scheme);
    }


    // *********************************
    // Parameters validation
    // *********************************

    @Override
    protected Result verifyParameters(Map<String, Object> parameters) {

        ResultBuilder builder = ResultBuilder.withStatusAndScope(Result.Status.OK, Scope.PARAMETERS).error(ResultErrorHelper.requiresOption("host", parameters))
                .error(ResultErrorHelper.requiresOption("server", parameters));

        // Validate using the catalog

        super.verifyParametersAgainstCatalog(builder, parameters);

        return builder.build();
    }


    // *********************************
    // Connectivity validation
    // *********************************

    @Override
    protected Result verifyConnectivity(Map<String, Object> parameters) {
        ResultBuilder builder = ResultBuilder.withStatusAndScope(Result.Status.OK, Scope.CONNECTIVITY);

        try {
            CICSConfiguration configuration = setProperties(new CICSConfiguration(), parameters);
            CICSAdapter cicsAdapter = new CICSAdapter(configuration);
            cicsAdapter.open();
        } catch (IOException e) {
            ResultErrorBuilder errorBuilder = ResultErrorBuilder.withCodeAndDescription(VerificationError.StandardCode.AUTHENTICATION, e.getMessage())
                    .detail("cics_exception_message", e.getMessage()).detail(VerificationError.ExceptionAttribute.EXCEPTION_CLASS, e.getClass().getName())
                    .detail(VerificationError.ExceptionAttribute.EXCEPTION_INSTANCE, e);

            builder.error(errorBuilder.build());
        } catch (Exception e) {
            builder.error(ResultErrorBuilder.withException(e).build());
        }
        return builder.build();
    }
}
