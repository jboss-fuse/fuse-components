package com.redhat.camel.component.cics.support;

import java.nio.charset.Charset;

public class CICSUtils {

    public static byte[] transcode(byte[] source, Charset from, Charset to) {
        return new String(source, from).getBytes(to);
    }

    public static  byte[] getBytes(String source, String encoding) throws java.io.UnsupportedEncodingException {
        if (null != encoding) {
            return source.getBytes(encoding);
        }
        return source.getBytes();
    }
}
