package com.logicbig.example.resource;

import java.util.Arrays;

import javax.annotation.Resource;


public class OrderServiceClient {

	@Resource(name = "OrderServiceA")
    private OrderService orderService;

    public void showPendingOrderDetails () {
        for (String orderId : Arrays.asList("100", "200", "300")) {
            System.out.println(orderService.getOrderDetails(orderId));
        }
    }
}