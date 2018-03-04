package com.logicbig.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@ContextConfiguration(inheritLocations = false,
        classes = {
                AppConfigExtended.class, OtherAppConfig.class
        })
//@ContextConfiguration(classes=AppConfigExtended.class)
public class ExtendedServiceTests extends BaseServiceTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void testOrderCustomer() {
        String orders = orderService.getOrdersForCustomer("323");
        System.out.println("-- OrdersByCustomer test --");
        System.out.println(orders);
        Assert.notNull(orders, "Orders is null for id 323");
    }
}