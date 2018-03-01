package com.logicbig.example.componentscan;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Lazy
@Component("basic-service")
public class ServiceImplA implements MyService {

    @PostConstruct
    private void init() {
        System.out.println("initializing lazily " +
                this.getClass().getSimpleName());
    }

    @Override
    public String getMessage() {
        return "Message from " + getClass().getSimpleName();
    }
}