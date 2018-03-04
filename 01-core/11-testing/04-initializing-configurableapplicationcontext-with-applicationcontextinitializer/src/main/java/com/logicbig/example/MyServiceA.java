package com.logicbig.example;

public class MyServiceA implements MyService {

    @Override
    public String doSomething() {
        return "in " + this.getClass().getSimpleName();
    }
}