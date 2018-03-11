package com.logicbig.example;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public int placeOrder(OrderItem order) {
        int id = orderItems.size() + 1;
        order.setId(id);
        orderItems.add(order);
        return id;
    }

    @Override
    public List<OrderItem> getOrderList() {
        return orderItems;
    }
}