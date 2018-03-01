package com.logicbig.example.autowiring;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class AutowireNo {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context = new
                            AnnotationConfigApplicationContext(Config.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
    }

    @Configuration
    public static class Config {

        @Bean(autowire = Autowire.NO)
        public ClientBean clientBean () {
            return new ClientBean();
        }

        @Bean
        public ServiceBean serviceBean () {
            return new ServiceBean("Service bean 1");
        }
    }

    private static class ClientBean {
        @Autowired
        private ServiceBean serviceBean;

        public void doSomething () {
            System.out.println(serviceBean.getMsg());
        }
    }

    private static class ServiceBean {
        private String msg;

        public ServiceBean (String msg) {
            this.msg = msg;
        }

        public String getMsg () {
            return msg;
        }
    }
}