package com.redhat.camel.component.cics.support;

import java.util.HashMap;
import java.util.Map;

public enum CICSDataExchangeType {

    CHANNEL("channel"),
    COMMAREA("commarea");


    private final String value;

    private final static Map<String, CICSDataExchangeType> CONSTANTS = new HashMap<>();

    static {
        for (CICSDataExchangeType c : values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    CICSDataExchangeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String value() {
        return this.value;
    }

    public static CICSDataExchangeType fromValue(String value) {
        if(value == null){
            throw new IllegalArgumentException(value);
        }
        CICSDataExchangeType constant = CONSTANTS.get(value.trim().toLowerCase());
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
