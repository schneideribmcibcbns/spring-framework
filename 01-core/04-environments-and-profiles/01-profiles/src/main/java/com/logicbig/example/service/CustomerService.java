package com.logicbig.example.service;


import com.logicbig.example.Customer;

import java.util.List;

public class CustomerService extends DataService<Customer> {

    //not showing transactional stuff

    public Customer getCustomerById(long id) {
        return getDao().load(id);
    }

    public void updateCustomer(Customer customer) {
        getDao().saveOrUpdate(customer);
    }

    public void saveCustomer(Customer customer) {
        getDao().saveOrUpdate(customer);
    }

    public List<Customer> getAllCustomers() {
        return getDao().loadAll();
    }
}