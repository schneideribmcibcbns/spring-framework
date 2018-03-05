package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class JConfigPropertySourceExample3 {

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

        @Bean
        @Qualifier("tradePrice")
        public Double getPrice (Environment env) {
            NumberFormat numberFormat = new DecimalFormat("##,###.00");

            CustomNumberEditor customNumberEditor = new CustomNumberEditor(Double.class,
                                                                           numberFormat, true);
            customNumberEditor.setAsText(env.getProperty("thePrice"));
            return (Double) customNumberEditor.getValue();
        }
    }

    public static class ClientBean {
        @Autowired
        @Qualifier("tradePrice")
        private Double price;


        public void doSomething () {
            System.out.printf("The price from prop file is %f%n", price);
        }
    }
}