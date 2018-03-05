package com.logicbig.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@ComponentScan
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

/*    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new TheCustomConverter());
    }*/

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new TheCustomConverter());
    }

    /*It doesn't work
    @Bean
    public HttpMessageConverter messageConverter() {
          return new TheCustomConverter();
    }*/
}