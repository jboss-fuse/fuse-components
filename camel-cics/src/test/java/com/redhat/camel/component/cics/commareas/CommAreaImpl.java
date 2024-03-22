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
public abstract class CommAreaImpl implements CommArea {

    public static final String ENCODING_CP1145 = "Cp1145";

    public static final String ENCODING_CP285 = "Cp285";

    public static final byte BYTE_SPACE = 64;

    protected byte[] getBytes(String source, String encoding) throws java.io.UnsupportedEncodingException {
        if (null != encoding) {
            return source.getBytes(encoding);
        }
        return source.getBytes();
    }

    protected void fillBytes(byte paramByte, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
            throws ArrayIndexOutOfBoundsException {
        int i = 1;
        paramArrayOfByte[paramInt1] = paramByte;
        while (i * 2 <= paramInt2) {
            System.arraycopy(paramArrayOfByte, paramInt1, paramArrayOfByte, paramInt1 + i, i);
            i *= 2;
        }
        System.arraycopy(paramArrayOfByte, paramInt1, paramArrayOfByte, paramInt1 + i, paramInt2 - i);
    }

    protected void marshallData(String data, byte[] byteCommArea, int start) {
        byte[] srcData;
        try {
            srcData = data.getBytes(ENCODING_CP1145);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Code page not supported:" + ENCODING_CP1145, e);
        }
        System.arraycopy(srcData, 0, byteCommArea, start, srcData.length);
    }

}