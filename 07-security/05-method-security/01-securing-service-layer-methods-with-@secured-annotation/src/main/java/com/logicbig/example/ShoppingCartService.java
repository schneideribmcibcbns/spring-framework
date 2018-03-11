package com.logicbig.example;

import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface ShoppingCartService {
    @Secured("ROLE_CUSTOMER")
    int placeOrder(OrderItem order);

    @Secured("ROLE_ADMIN")
    List<OrderItem> getOrderList();
}