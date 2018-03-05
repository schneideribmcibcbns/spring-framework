package com.logicbig.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableWebMvc
@ComponentScan("com.logicbig.example")
public class AppConfig extends WebMvcConfigurerAdapter {
   /* @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //it will replace the default ones
        converters.add(new CsvHttpMessageConverter2<>());
    }*/
    
    @Override
    public void extendMessageConverters (List<HttpMessageConverter<?>> converters) {
        converters.add(new CsvHttpMessageConverter<>());
    }
}