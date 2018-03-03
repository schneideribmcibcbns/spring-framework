package com.logicbig.example;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCounterInterceptor extends HandlerInterceptorAdapter {
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {

        request.setAttribute("visitorCounter", counter.incrementAndGet());
        return true;
    }
}