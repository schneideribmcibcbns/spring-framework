package com.logicbig.example;

public class MyServiceB implements MyService {
    @Override
    public String doSomething() {
        return "in " + this.getClass().getSimpleName();
    }
}