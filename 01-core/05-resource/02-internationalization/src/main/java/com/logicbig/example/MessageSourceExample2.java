package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.IOException;
import java.util.Locale;

public class MessageSourceExample2 {
    public static void main (String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        //uncomment next line to change the locale
        //   Locale.setDefault(Locale.FRANCE);
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(Config.class);

        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }

    @Configuration
    public static class Config {

        @Bean
        public MyBean myBean () {
            return new MyBean();
        }

        @Bean
        public MessageSource messageSource () {
            ReloadableResourceBundleMessageSource messageSource =
                                new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:messages/msg");
            messageSource.setDefaultEncoding("UTF-8");
            messageSource.setCacheMillis(500);
            return messageSource;
        }
    }

    public static class MyBean {
        @Autowired
        private MessageSource messageSource;

        public void doSomething () {
            //we are repeating 20 times with 2sec sleep, so that we can modify the
            //files outside (in target/classes folder) to watch the change reloading.
            for (int i = 0; i < 20; i++) {
                System.out.println(messageSource.getMessage("app.name",
                                                            new Object[]{"Joe"},
                                                            Locale.getDefault()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}