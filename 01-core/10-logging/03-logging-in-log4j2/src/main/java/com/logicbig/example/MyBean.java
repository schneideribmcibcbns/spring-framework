package com.logicbig.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBean {
    private static Log log = LogFactory.getLog(MyBean.class);

    public void doSomething() {
        log.info("doing something");
    }
}