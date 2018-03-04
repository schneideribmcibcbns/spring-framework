package com.logicbig.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestExecutionListeners(value = {MyListener.class,
        DependencyInjectionTestExecutionListener.class})
public class MyTests {

    @Autowired
    private MyBean myBean;

    @Test
    public void testDoSomething() {
        myBean.doSomething();
    }
}