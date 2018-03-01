package com.logicbig.example.client;

import com.logicbig.example.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;


public class HelloWorldServiceClient {

    @Autowired
    private HelloWorldService helloWorld;

    public void showMessage() {
        helloWorld.sayHi("Hello world!");
    }
}
