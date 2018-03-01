package com.logicbig.example.componentscan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.logicbig.example.componentscan")
public class AppConfig {

    public static void main(String... strings) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Spring container started and is ready");
        MyPrototypeBean bean = context.getBean(MyPrototypeBean.class);
        bean.doSomething();
    }
}