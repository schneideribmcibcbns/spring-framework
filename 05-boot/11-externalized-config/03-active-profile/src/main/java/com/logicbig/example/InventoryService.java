package com.logicbig.example;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface InventoryService {
    void addStockItem(String itemName, int qty, BigDecimal unitPrice);
}