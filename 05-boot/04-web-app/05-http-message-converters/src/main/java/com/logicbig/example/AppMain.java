package com.logicbig.example;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppMain {
    //adding additional converters
/*    @Bean
    public HttpMessageConverters additionalConverters() {
        return new HttpMessageConverters(new TheCustomConverter());
    }*/

    //replacing default converters
    @Bean
    public HttpMessageConverters converters() {
        return new HttpMessageConverters(
                false, Arrays.asList(new TheCustomConverter()));
    }

    /* registering additional converter
    @Bean
    public HttpMessageConverter<?> messageConverter(){
        return new TheCustomConverter();
    }*/


    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }
}