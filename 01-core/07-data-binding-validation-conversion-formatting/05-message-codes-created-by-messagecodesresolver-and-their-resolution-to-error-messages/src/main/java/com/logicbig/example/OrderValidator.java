package com.logicbig.example;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.math.BigDecimal;

public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "orderDate", "date.empty",
                "Date cannot be empty");
        ValidationUtils.rejectIfEmpty(errors, "orderPrice", "price.empty",
                "Price cannot be empty");
        Order order = (Order) target;
        if (order.getOrderPrice() != null &&
                order.getOrderPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("orderPrice", "price.invalid", "Price must be greater than 0");
        }
    }
}