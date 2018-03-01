package com.logicbig.example.implicitconstructorinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceClient {

    private OrderService orderService;

    //@Autowired is no longer required in Spring 4.3 and later.
    public OrderServiceClient (OrderService orderService) {
        this.orderService = orderService;
    }

    public void showPendingOrderDetails () {
        System.out.println(orderService.getOrderDetails("100"));
    }
}