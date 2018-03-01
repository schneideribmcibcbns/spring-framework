package com.logicbig.example.defaultmethod;

import javax.annotation.PostConstruct;

public interface IMyBean {
    @PostConstruct
    default void init() {
        System.out.println("post construct: "+this.getClass().getSimpleName());
    }
}