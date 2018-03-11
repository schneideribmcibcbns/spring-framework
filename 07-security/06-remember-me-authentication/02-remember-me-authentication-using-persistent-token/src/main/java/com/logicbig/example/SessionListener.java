package com.logicbig.example;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("----\nsession created\n------");
        event.getSession().setMaxInactiveInterval(30);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("----\nsession destroyed\n------");
    }
}