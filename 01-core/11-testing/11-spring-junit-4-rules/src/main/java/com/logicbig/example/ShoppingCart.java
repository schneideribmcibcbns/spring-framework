package com.logicbig.example;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShoppingCart {
    private List<String> orders = new ArrayList<>();

    public void addItem(String name, int qty) {
        orders.add(String.format("order. Item:%s qty%s", name, qty));
    }

    public String checkout() {
        String msg = placeOrders();
        orders.clear();
        return msg;
    }

    public String placeOrders() {
        return orders.size() + " orders placed";
    }
}