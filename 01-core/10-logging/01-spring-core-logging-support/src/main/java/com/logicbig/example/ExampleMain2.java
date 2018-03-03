package com.logicbig.example;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleMain2 {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        String path = ExampleMain2.class.getClassLoader()
                                       .getResource("logging.properties")
                                       .getFile();
        System.setProperty("java.util.logging.config.file", path);

        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext(ExampleMain2.class);

        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }
}