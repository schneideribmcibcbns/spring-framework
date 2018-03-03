package com.logicbig.example;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
public class ExampleMain {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext(ExampleMain.class);

        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }
}