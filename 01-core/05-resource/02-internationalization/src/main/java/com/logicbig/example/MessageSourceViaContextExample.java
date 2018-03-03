package com.logicbig.example;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.IOException;
import java.util.Locale;

public class MessageSourceViaContextExample {
    public static void main (String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        //uncomment next line to change the locale
        // Locale.setDefault(Locale.FRANCE);
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(Config.class);

        System.out.println(context.getMessage("app.name", new Object[]{"Joe"},
                                              Locale.getDefault()));

    }

    @Configuration
    public static class Config {

        @Bean
        public MessageSource messageSource () {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("messages/msg");
            return messageSource;
        }
    }
}