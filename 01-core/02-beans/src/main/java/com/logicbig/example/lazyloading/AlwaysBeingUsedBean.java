package com.logicbig.example.lazyloading;

import javax.annotation.PostConstruct;

public class AlwaysBeingUsedBean {

    @PostConstruct
    public void init() {
        System.out.println("AlwaysBeingUsedBean initializing");
    }
}