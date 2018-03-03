package com.logicbig.example.service;

import com.logicbig.example.Customer;
import org.fluttercode.datafactory.impl.DataFactory;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static List<Customer> createTestData(){
        List<Customer> customers = new ArrayList<>();
        DataFactory data = new DataFactory();
        for (int i = 0; i < 10; i++) {
            Customer c = new Customer();
            c.setCustomerId(i + 1);
            c.setName(data.getName());
            c.setAddress(data.getAddress());
            c.setPhone(data.getNumberText(10));
            customers.add(c);
        }
        return customers;
    }
}