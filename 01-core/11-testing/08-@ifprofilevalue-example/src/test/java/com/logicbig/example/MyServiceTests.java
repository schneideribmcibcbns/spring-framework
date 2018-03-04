package com.logicbig.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class MyServiceTests {

    @Autowired
    private MyService myService;

    @Test
    @IfProfileValue(name = "os.name", values = {"Windows 10", "Windows 7"})
    public void testMyService() {
        String s = myService.getMsg();
        System.out.println(s);
        Assert.assertEquals("some msg", s);
    }
}