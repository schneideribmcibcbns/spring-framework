package com.logicbig.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public enum DataServiceLocal implements DataService {
    Instance;

    private List<Customer> customers = new ArrayList<>();

    DataServiceLocal() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 1; i <= 100; i++) {
            Customer c = new Customer();
            c.setCustomerId(i);
            c.setName("Name" + i);
            c.setAge(rnd.nextInt(18, 90));
            customers.add(c);
        }
    }

    @Override
    public List<Customer> getCustomersByAge(int minAge, int maxAge) {
        return customers.stream()
                        .filter(c -> c.getAge() >= minAge && c.getAge() < maxAge)
                        .collect(Collectors.toList());
    }
}