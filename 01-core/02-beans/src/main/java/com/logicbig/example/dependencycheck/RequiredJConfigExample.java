package com.logicbig.example.dependencycheck;


import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequiredJConfigExample {

    @Bean(autowire = Autowire.BY_TYPE)
    public ClientBean clientBean () {
        return new ClientBean();
    }

    // remove this comment to fix BeanInitializationException
   /* @Bean
    public ServiceBean serviceBean () {
        return new ServiceBean();
    }*/

    
    @Bean
    public RequiredAnnotationBeanPostProcessor processor () {
        return new RequiredAnnotationBeanPostProcessor() {
            @Override
            protected boolean shouldSkip (ConfigurableListableBeanFactory beanFactory,
                                          String beanName) {
                if (beanName.equals("clientBean")) {
                    return false;
                }
                return super.shouldSkip(beanFactory, beanName);
            }
        };
    }
    
    public static void main (String... strings) {

        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                RequiredJConfigExample.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.work();

    }

    public static class ClientBean {
        private ServiceBean serviceBean;

        @Required
        public void setServiceBean (ServiceBean serviceBean) {
            this.serviceBean = serviceBean;
        }

        public void work () {
            System.out.println("service bean instance: " + serviceBean);
        }
    }

    public static class ServiceBean {
    }
}