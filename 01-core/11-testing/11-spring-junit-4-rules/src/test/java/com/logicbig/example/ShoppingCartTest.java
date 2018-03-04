package com.logicbig.example;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@ContextConfiguration(classes = AppConfig.class)
public class ShoppingCartTest {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE= new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private ShoppingCart shoppingCart;

    @Test
    public void testCheckout() {
        shoppingCart.addItem("Item1", 3);
        shoppingCart.addItem("item2", 5);
        String result = shoppingCart.checkout();
        System.out.printf("Shopping cart checkout response: %s%n", result);
        Assert.assertEquals("2 orders placed", result);
    }
}