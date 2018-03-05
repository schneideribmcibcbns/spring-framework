package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@EnableWebMvc
public class AppConfig {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        System.out.println("-- context refreshed --");
        System.out.println("context: "+
                event.getApplicationContext());

        handlerAdapter.getMessageConverters()
                      .stream()
                      .forEach(System.out::println);
        System.out.println("-------");
    }
}