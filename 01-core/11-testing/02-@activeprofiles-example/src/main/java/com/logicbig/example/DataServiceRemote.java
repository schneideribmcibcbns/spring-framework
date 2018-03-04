package com.logicbig.example;

import java.util.List;

public enum DataServiceRemote implements DataService {
    Instance;

    @Override
    public List<Customer> getCustomersByAge(int minAge, int maxAge) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}