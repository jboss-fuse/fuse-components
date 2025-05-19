package com.redhat.camel.component.cics;

import org.junit.jupiter.api.Test;

public class CustomBindingWithDataExchange extends CustomBindingTest {
    @Test
    public void checkCustomBindinginRegistry(){
        CICSComponent component = (CICSComponent) context.getComponent("cics",false);
        component.getConfiguration().getEciBinding();
    }

    @Override
    protected String getOptions(String host, int port) {
        return "/commarea?host="+host+"&port="+port+"&protocol=tcp&eciBinding=#customBinding&userId=foo&password=bar";
    }
}
