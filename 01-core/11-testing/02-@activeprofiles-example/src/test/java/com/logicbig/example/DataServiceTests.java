package com.logicbig.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles(AppConfig.PROFILE_LOCAL)
public class DataServiceTests {

    @Autowired
    private DataService dataService;

    @Test
    public void testCustomerAges() {
        List<Customer> customers = dataService.getCustomersByAge(25, 40);
        for (Customer customer : customers) {
            int age = customer.getAge();
            Assert.assertTrue("Age range is not 25 to 40: " + customer,
                    age >= 25 && age < 40);
        }
    }
}