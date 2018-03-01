package com.logicbig.example.componentscan;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceImplB implements MyService {

    @PostConstruct
    private void init(){
        System.out.println("initializing at start up " +
                this.getClass().getSimpleName());
    }
    @Override
    public String getMessage() {
        return "Message from "+getClass().getSimpleName();
    }
}