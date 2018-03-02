package com.logicbig.example;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class MyBean {
    
    @EventListener
    public void handleEvent (RequestHandledEvent e) {
        System.out.println("-- RequestHandledEvent --");
        System.out.println(e);
    }
}