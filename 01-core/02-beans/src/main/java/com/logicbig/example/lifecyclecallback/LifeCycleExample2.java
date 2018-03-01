package com.logicbig.example.lifecyclecallback;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifeCycleExample2 {

    @Bean(initMethod = "myPostConstruct", destroyMethod = "cleanUp")
    public MyBean2 myBean2() {
        return new MyBean2();
    }

    @Bean
    public OtherBean otherBean() {
        return new OtherBean();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext(LifeCycleExample2.class);

        context.registerShutdownHook();

        System.out.println("-- accessing bean --");
        MyBean2 bean = context.getBean(MyBean2.class);
        bean.doSomething();

        System.out.println("-- finished --");
    }
}