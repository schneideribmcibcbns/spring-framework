package com.logicbig.example.autowiring;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * In this example we are explicitly giving the bean a name by using @Bean(name =....)
 */
public class AutowireByName2 {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context = new
                            AnnotationConfigApplicationContext(Config.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
    }

    @Configuration
    public static class Config {

        @Bean(autowire = Autowire.BY_NAME)
        public ClientBean clientBean () {
            return new ClientBean();
        }

        @Bean(name = "someOtherServiceBean")
        public ServiceBean serviceBean1 () {
            return new ServiceBean("Service bean 1");
        }

        @Bean
        public ServiceBean serviceBean2 () {
            return new ServiceBean("Service bean 2");
        }
    }

    private static class ClientBean {
        private ServiceBean someOtherServiceBean;

        @Autowired
        public void setSomeOtherServiceBean (ServiceBean serviceBean) {
            this.someOtherServiceBean = serviceBean;
        }

        public void doSomething () {
            System.out.println(someOtherServiceBean.getMsg());
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