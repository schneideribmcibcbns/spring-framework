package com.logicbig.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Currency;

public class JConfigPropertySourceExample {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(Config.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
    }

    @Configuration
    @PropertySource("classpath:app.properties")
    public static class Config {
        @Bean
        public ClientBean clientBean () {
            return new ClientBean();
        }
    }

    public static class ClientBean {
        @Value("${theCurrency}")
        private Currency currency;

        public void doSomething () {
            System.out.printf("The currency from prop file is %s%n", currency);
            System.out.printf("The currency name is %s%n", currency.getDisplayName());
        }
    }
}