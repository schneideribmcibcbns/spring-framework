package com.logicbig.example;

import org.springframework.stereotype.Component;

@Component
public class MyBean {
    public void doSomething() {
        System.out.println("-- in MyBean.doSomething() method --");
    }
}