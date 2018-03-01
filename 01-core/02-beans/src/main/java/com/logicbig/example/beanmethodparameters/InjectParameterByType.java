package com.logicbig.example.beanmethodparameters;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class InjectParameterByType {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                Config.class);
        BeanB beanB = context.getBean(BeanB.class);
        System.out.println("In the main method: " + beanB.getBeanA());
    }

    @Configuration
    public static class Config {

        @Bean
        public BeanA bean1 () {
            BeanA beanA = new BeanA();
            System.out.println("method bean1: beanA created = " + beanA);
            return beanA;
        }

        @Bean
        public BeanB bean2 (BeanA theBean) {
            BeanB beanB = new BeanB(theBean);
            System.out.println("method bean2: beanB created = " + beanB +
                                "\n with constructor param BeanA = " + theBean);

            return beanB;
        }
    }
}