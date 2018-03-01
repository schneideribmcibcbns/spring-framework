package com.logicbig.example.autowiring;


import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class AutowireNo2 {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context = new
                            AnnotationConfigApplicationContext(Config.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
    }

    public static class Config {

        @Bean(autowire = Autowire.NO)
        public ClientBean clientBean () {
            return new ClientBean();
        }

        @Bean
        public ServiceBean serviceBean () {
            return new ServiceBean("Service Bean 1");
        }

        @Bean
        public ServiceBean serviceBean2 () {
            return new ServiceBean("Service Bean 2");
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