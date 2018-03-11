package com.logicbig.example;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface ShoppingCartService {
    @RolesAllowed("ROLE_CUSTOMER")
    int placeOrder(OrderItem order);

    @RolesAllowed("ROLE_ADMIN")
    List<OrderItem> getOrderList();
}