package com.logicbig.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBean {
    private static Logger log = LoggerFactory.getLogger(MyBean.class);

    public void doSomething() {
        log.info("doing something");
    }
}