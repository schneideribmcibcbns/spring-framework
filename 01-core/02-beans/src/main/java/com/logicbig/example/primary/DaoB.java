package com.logicbig.example.primary;


public class DaoB implements Dao {

    @Override
    public void saveOrder(String orderId) {
        System.out.println("DaoB Order saved " + orderId);
    }
}