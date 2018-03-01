package com.logicbig.example.lazyinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

public class App {

    public static void main (String[] args) {
        ApplicationContext context =
                  new AnnotationConfigApplicationContext(
                            MyConfig.class);
        System.out.println("--- container initialized ---");
        MyEagerBean bean = context.getBean(MyEagerBean.class);
        System.out.println("MyEagerBean retrieved from bean factory");
        bean.doSomethingWithLazyBean();
    }

    public static class MyConfig {

        @Bean
        public MyEagerBean eagerBean () {
            return new MyEagerBean();
        }

        @Bean
        @Lazy
        public MyLazyBean lazyBean () {
            return new MyLazyBean();
        }
    }
}