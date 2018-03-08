package com.logicbig.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class ExampleMain {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication sa = new SpringApplication();
        sa.addListeners(new MyApplicationListener());
        sa.setSources(new HashSet<>(Arrays.asList(ExampleMain.class)));
        ConfigurableApplicationContext context = sa.run(args);
        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }
}