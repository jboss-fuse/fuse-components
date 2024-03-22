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
package com.redhat.camel.component.cics.commareas;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author Sergio Gutierrez (sgutierr@redhat.com)
 * @author Jose Roman Martin Gil (rmarting@redhat.com)
 */
public class EC01Impl extends CommAreaImpl implements EC01 {

    private static final int COMMAREA_SIZE = 18;

    private final byte[] byteCommArea = new byte[COMMAREA_SIZE];

    private final String commArea = "";

    @Override
    public String getData() {
        return this.commArea;
    }

    @Override
    public byte[] getDataBuffer() {
        try {
            System.arraycopy(getBytes(this.commArea, ENCODING_CP1145), 0, byteCommArea, 0,
                    Math.min(byteCommArea.length, this.commArea.length()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return this.byteCommArea;
    }

}