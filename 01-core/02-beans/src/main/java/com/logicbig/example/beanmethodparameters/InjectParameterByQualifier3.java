package com.logicbig.example.beanmethodparameters;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class InjectParameterByQualifier3 {

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
        @Qualifier("myBean")
        public BeanA bean2 () {
            BeanA beanA = new BeanA();
            System.out.println("method bean2: beanA created = " + beanA);
            return beanA;
        }

        @Bean
        public BeanC bean3 () {
            BeanC beanC = new BeanC();
            System.out.println("method bean3: beanC created = " + beanC);
            return beanC;
        }

        @Bean
        @Qualifier("myBean2")
        public BeanC bean4 () {
            BeanC beanC = new BeanC();
            System.out.println("method bean4: beanC created = " + beanC);
            return beanC;
        }

        @Bean
        public BeanB bean5 (@Qualifier("myBean") BeanA theBean,
                            @Qualifier("myBean2") BeanC theBean2) {
            BeanB beanB = new BeanB(theBean);
            System.out.println("method bean5: beanB created = " + beanB +
                                "\n with constructor param of type BeanA= " + theBean);
            System.out.println("method bean5: theBean2 instance (can also be in as constructor " +
                                "arg or some " +
                                "other way): " + theBean2);

            return beanB;
        }
    }
}