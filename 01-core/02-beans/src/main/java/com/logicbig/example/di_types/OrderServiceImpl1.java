package com.logicbig.example.di_types;

public class OrderServiceImpl1 implements OrderService {

    public String getOrderDetails(String orderId) {
        return "Order details from impl 1, for order id=" + orderId;
    }
}