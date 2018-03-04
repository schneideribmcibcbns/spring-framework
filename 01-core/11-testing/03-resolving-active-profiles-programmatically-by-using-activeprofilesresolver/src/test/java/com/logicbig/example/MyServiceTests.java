package com.logicbig.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles(resolver = MyActiveProfilesResolver.class)
public class MyServiceTests {

    @Autowired
    private MyService dataService;

    @Test
    public void testDoSomething() {
        String s = dataService.doSomething();
        System.out.println(s);
        Assert.assertEquals("in MyServiceA", s);
    }
}