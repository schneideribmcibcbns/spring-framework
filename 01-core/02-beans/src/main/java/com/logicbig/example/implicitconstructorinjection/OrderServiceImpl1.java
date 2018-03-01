package com.logicbig.example.implicitconstructorinjection;

import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl1 implements OrderService {

    public String getOrderDetails(String orderId) {
        return "Order details from impl 1, for order id=" + orderId;
    }
    
    @Override
    public String toString() {
    	return "OrderServiceImpl1";
    }
}