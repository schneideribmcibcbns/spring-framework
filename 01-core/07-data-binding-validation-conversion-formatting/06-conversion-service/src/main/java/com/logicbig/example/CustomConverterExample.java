package com.logicbig.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CustomConverterExample {
    public static void main (String[] args) {
        AnnotationConfigApplicationContext context = new
                            AnnotationConfigApplicationContext(
                            Config.class);

        ClientBean clientBean = context.getBean(ClientBean.class);
        clientBean.showLocalDateTime();
    }

    @Configuration
    public static class Config {
        @Bean
        public ConversionService conversionService () {
            DefaultConversionService service = new DefaultConversionService();
            service.addConverter(new DateToLocalDateTimeConverter());
            return service;
        }

        @Bean
        public ClientBean clientBean () {
            return new ClientBean(new Date());
        }
    }

    private static class ClientBean {
        private final Date date;
        @Autowired
        private ConversionService conversionService;

        private ClientBean (Date date) {
            this.date = date;
        }

        public void showLocalDateTime () {
            LocalDateTime dateTime = conversionService.convert(date,
                                                       LocalDateTime.class);
            System.out.println(dateTime);
        }

    }

    private static class DateToLocalDateTimeConverter
                        implements Converter<Date, LocalDateTime> {

        @Override
        public LocalDateTime convert (Date source) {
            return LocalDateTime.ofInstant(source.toInstant(),
                                ZoneId.systemDefault());
        }
    }
}