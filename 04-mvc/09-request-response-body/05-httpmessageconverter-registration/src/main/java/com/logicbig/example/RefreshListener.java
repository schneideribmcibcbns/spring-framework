package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Component
public class RefreshListener {
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        handlerAdapter.getMessageConverters().stream()
                      .map(c -> c.toString())
                      .forEach(System.out::println);
    }
}