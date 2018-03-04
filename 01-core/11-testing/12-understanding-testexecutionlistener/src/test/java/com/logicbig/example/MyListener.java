package com.logicbig.example;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class MyListener implements TestExecutionListener {
    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        System.out.println("MyListener.beforeTestClass()");
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        System.out.println("MyListener.prepareTestInstance()");

    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        System.out.println("MyListener.beforeTestMethod()");
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        System.out.println("MyListener.afterTestMethod()");
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        System.out.println("MyListener.afterTestClass");
    }
}