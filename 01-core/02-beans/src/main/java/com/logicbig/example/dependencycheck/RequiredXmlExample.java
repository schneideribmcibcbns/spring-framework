package com.logicbig.example.dependencycheck;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RequiredXmlExample {

    public static void main (String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        RequiredJConfigExample.ClientBean bean = context.getBean(
                            RequiredJConfigExample.ClientBean.class);
        bean.work();
    }
}
