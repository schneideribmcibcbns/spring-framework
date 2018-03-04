package com.logicbig.example;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    String getOrdersForCustomer(String customerId);
}