package com.logicbig.example;

import java.math.BigDecimal;

public class InMemoryInventoryServiceImpl implements InventoryService {
    @Override
    public void addStockItem(String itemName, int qty, BigDecimal unitPrice) {
        System.out.println("adding item in InMemoryInventoryServiceImpl: " + itemName);
    }
}