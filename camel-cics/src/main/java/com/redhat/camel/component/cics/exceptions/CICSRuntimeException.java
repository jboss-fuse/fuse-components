package com.redhat.camel.component.cics.exceptions;

public class CICSRuntimeException extends RuntimeException{
    private static final long serialVersionUID = -2946266495682282677L;

    public CICSRuntimeException(String message) {
        super(message);
    }

    public CICSRuntimeException(Throwable e) {
        super(e);
    }

    public CICSRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
