package com.logicbig.example.resource;

public class OrderServiceImpl2 implements OrderService {

    public String getOrderDetails(String orderId) {
        return "Order details from impl 1, for order id=" + orderId;
    }
}