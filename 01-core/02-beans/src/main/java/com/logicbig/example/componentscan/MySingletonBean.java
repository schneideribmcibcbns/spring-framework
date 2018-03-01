package com.logicbig.example.componentscan;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MySingletonBean {

    @PostConstruct
    public void init() {
        System.out.println("initializing " +
                this.getClass().getSimpleName());
    }
}