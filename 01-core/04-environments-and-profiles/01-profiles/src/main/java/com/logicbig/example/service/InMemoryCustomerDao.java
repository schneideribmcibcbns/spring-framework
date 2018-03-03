package com.logicbig.example.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.logicbig.example.Customer;

public class InMemoryCustomerDao implements Dao<Customer> {

    private final Set<Customer> customers = new HashSet<>(
            DataUtil.createTestData());

    @Override
    public void saveOrUpdate(Customer customer) {
        //since we have set it will replace the existing one
        customers.add(customer);
    }

    
    @Override
    public Customer load(long id) {
        Optional<Customer> first = customers.stream()
                                            .filter(c -> c.getCustomerId() == id)
                                            .findFirst();
        return first.isPresent() ? first.get() : null;
    }

    


    @Override
    public List<Customer> loadAll() {
        return new ArrayList<>(customers);
    }
}