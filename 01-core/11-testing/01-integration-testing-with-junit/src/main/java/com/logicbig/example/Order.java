package com.logicbig.example;

public class Order {
    private String item;
    private int qty;

    public Order(String item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public String getItem() {
        return item;
    }

    public int getQty() {
        return qty;
    }
}