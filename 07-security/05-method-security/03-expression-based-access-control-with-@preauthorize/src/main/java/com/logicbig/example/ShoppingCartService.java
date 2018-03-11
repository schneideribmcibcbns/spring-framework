package com.logicbig.example;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ShoppingCartService {
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    int placeOrder(OrderItem order);

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<OrderItem> getOrderList();
}