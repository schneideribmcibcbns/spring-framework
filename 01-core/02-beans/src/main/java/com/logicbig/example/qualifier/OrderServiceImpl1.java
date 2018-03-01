package com.logicbig.example.qualifier;

public class OrderServiceImpl1 implements OrderService {

    public String getOrderDetails(String orderId) {
        return "Order details from impl 1, for order id=" + orderId;
    }
}