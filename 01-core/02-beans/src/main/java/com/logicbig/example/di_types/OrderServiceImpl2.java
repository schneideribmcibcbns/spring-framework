package com.logicbig.example.di_types;

public class OrderServiceImpl2 implements OrderService {

    public String getOrderDetails(String orderId) {
        return "Order details from impl 2, for order id=" + orderId;
    }
}