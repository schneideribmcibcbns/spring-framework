package com.logicbig.example.lazyinjection;

import javax.annotation.PostConstruct;

public class MyLazyBean {

    @PostConstruct
    public void init () {
        System.out.println(getClass().getSimpleName() + " has been initialized");
    }

    public void doSomething () {
        System.out.println("inside lazy bean doSomething()");
    }
}