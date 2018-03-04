package com.logicbig.example;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MyBean {
    private static final Logger logger = Logger.getLogger(MyBean.class.getName());

    public void doSomething() {
        logger.info("some message");
    }
}