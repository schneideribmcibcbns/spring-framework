package com.logicbig.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class, initializers = MyContextInitializer.class)
public class BaseServiceTests {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCustomerById() {
        String customer = customerService.getCustomerById("323");
        System.out.println("-- CustomerById test --");
        System.out.println(customer);
        Assert.notNull(customer, "customer is null for id 323");
    }
}