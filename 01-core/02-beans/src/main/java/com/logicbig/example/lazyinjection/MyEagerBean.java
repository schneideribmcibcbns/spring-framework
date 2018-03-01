package com.logicbig.example.lazyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

public class MyEagerBean {

    @Autowired
    @Lazy
    private MyLazyBean myLazyBean;

    @PostConstruct
    public void init () {
        System.out.println(getClass().getSimpleName() + " has been initialized");
    }

    public void doSomethingWithLazyBean () {
        System.out.println("Using lazy bean");
        myLazyBean.doSomething();
    }
}