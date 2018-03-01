package com.logicbig.example;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationExample {
    @Bean
    public MyBean myBean () {
        return new MyBean();
    }

    public static void main (String[] args) {
        ApplicationContext ctx =
                            SpringApplication.run(ApplicationExample.class, args);
        MyBean bean = ctx.getBean(MyBean.class);
        bean.doSomething();
    }

    private static class MyBean {

        public void doSomething () {
            Bag bag = new HashBag();
            bag.add("ONE", 6);
            System.out.println("Doing something in MyBean");
            System.out.println(bag);
        }
    }
}