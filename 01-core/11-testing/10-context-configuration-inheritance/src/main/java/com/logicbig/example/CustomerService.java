package com.logicbig.example;

import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    String getCustomerById(String id);
}