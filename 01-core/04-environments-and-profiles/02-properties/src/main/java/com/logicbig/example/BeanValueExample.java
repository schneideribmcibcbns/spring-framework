package com.logicbig.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;



@Configuration
@PropertySource("classpath:app.properties")
public class BeanValueExample {
    @Bean
    public MyBean myBean () {
        return new MyBean();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev () {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(BeanValueExample.class);
        context.getBean(MyBean.class)
               .showProp();
    }

    
    public static class MyBean {
        @Value("${some-prop:defaultStr}")
        private String str;

        public void showProp () {
            System.out.println(str);
        }
    }
    
}