package com.logicbig.example.dependencycheck;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializingBeanExample {

    @Bean(autowire = Autowire.BY_TYPE)
    public ClientBean clientBean () {
        return new ClientBean();
    }

    //remove following comment to fix java.lang.IllegalArgumentException: ServiceBean not set
 /*   @Bean
    public ServiceBean serviceBean () {
        return new ServiceBean();
    }*/

    public static void main (String... strings) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                InitializingBeanExample.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.work();

    }

    public static class ClientBean implements InitializingBean {
        private ServiceBean serviceBean;

        public void setServiceBean (ServiceBean serviceBean) {
            this.serviceBean = serviceBean;
        }

        public void work () {
            System.out.println("service bean instance: " + serviceBean);
        }

        @Override
        public void afterPropertiesSet () throws Exception {
            if (serviceBean == null) {
                throw new IllegalArgumentException("ServiceBean not set");
            }
        }
    }

    public static class ServiceBean {
    }
}