package com.logicbig.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConfigPropertyExampleMain {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigPropertyExampleMain.class, args);
        MyAppProperties bean = context.getBean(MyAppProperties.class);
        System.out.println(bean);
        
        MyConfigBean bean2 = context.getBean(MyConfigBean.class);
        System.out.println(bean2);
    }
}