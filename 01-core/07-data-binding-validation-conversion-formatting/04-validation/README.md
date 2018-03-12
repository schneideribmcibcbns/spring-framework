# Validation

Spring core framework provides two approaches to validate beans:

* Spring specific validation mechanism by implementing spring `Validator` interface.

* JSR 349/303 annotation based validations. 

## Spring Validator example

### The bean to validate

```java
public class Order {
   private Date date;
   private BigDecimal price;

 //getters and setters
}
```

### Validator implementation

`org.springframework.validation.Validator` is the central interface of Spring core validation:

```java
package org.springframework.validation;

public interface Validator {
 	boolean supports(Class<?> clazz);
 	void validate(Object target, Errors errors);
}
```

In the following example we are going to implement it to validate our `Order` bean. `Validator#validate` is the method where we perform the validation.

```java
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

public class OrderValidator implements Validator {

   @Override
   public boolean supports (Class<?> clazz) {
       return Order.class == clazz;
   }

   @Override
   public void validate (Object target, Errors errors) {
       ValidationUtils.rejectIfEmpty(errors, "date", "date.empty");
       ValidationUtils.rejectIfEmpty(errors, "price", "price.empty");
       Order order = (Order) target;
       if (order.getPrice() != null &&
                        order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
           errors.rejectValue("price", "price.invalid");
       }
   }
}
```

In above example we used `ValidationUtils` which provides convenient static methods for performing validations.

Also the second argument of `Validator#validate` `org.springframework.validation.Errors` stores and exposes error messages. In this example we are going to use `DataBinder` for kicking off the validation process and collecting error messages. `DataBinder` internally creates an instance of `BindingResult`.

`BindingResult` extends `Errors` interface. The same instance of `Errors` is passed to `Validator#validate` method when `DataBinder#validate()` method is called.


### The Client bean

```java
 public class ClientBean {
    @Autowired
    private Order order;

    private void processOrder () {
        if (validateOrder()) {
            System.out.println("processing " + order);
        }
    }

    private boolean validateOrder () {
        DataBinder dataBinder = new DataBinder(order);
        dataBinder.addValidators(new OrderValidator());
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            ResourceBundleMessageSource messageSource =
					new ResourceBundleMessageSource();	
            messageSource.setBasename("ValidationMessages");
            System.out.println(messageSource.getMessage("order.invalid", null, Locale.US));
            dataBinder.getBindingResult().getAllErrors().stream().
                forEach(e -> System.out.println(messageSource.getMessage(e, Locale.US)));
            return false;
        }
        return true;
    }
 }
```

`ResourceBundleMessageSource` is used to get messages from resource bundle. Please read [this](../../05-resource/02-internationalization/README.md) if not already familiar with it.

`ClientBean` in above code is using too much Spring API, which is not considered a good practice as our beans should contain only application specific logic. May be, we should wrap `validateOrder()` logic into a new low-level bean which will encapsulate Spring low-level API to validate any object not just `Order` object. It should be similar to Spring MVC validation mechanism where Controller's handler methods are implicitly passed with `BindingResult` so that we can focus on client side logic only. Please explore [MVC form validation example](../../../04-mvc/06-form/02-jsr-349-bean-validation/README.md). Also there's a related example `ValidationMixedExample.java` in the source code.

### Registering beans to Spring container

```java
 @Configuration
 public static class Config {

     @Bean
     public ClientBean clientBean () {
         return new ClientBean();
     }

     @Bean
     public Order order () {
         //in real scenario this order should be coming from user interface
         Order order = new Order();
         order.setPrice(BigDecimal.ZERO);
         return order;
     }
 }
```

### The main method

```java
 public static void main (String[] args) {
     AnnotationConfigApplicationContext context =
                         new AnnotationConfigApplicationContext(
                                             Config.class);

     ClientBean bean = context.getBean(ClientBean.class);
     bean.processOrder();
 }
```

### Output:

```Powershell
Order has validation errors:
Date cannot be empty
Price should be more than zero
```

In above example we could have used following method instead of using `DataBinder`.  

```java
ValidationUtils#invokeValidator(Validator validator, Object obj, Errors errors)
```

Please refer to ValidatorExample2.java.


## JSR 349/303 annotation based validation Example

### Use validation annotations on the bean fields:

```java
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class Order {
  @NotNull(message = "{date.empty}")
  @Future(message = "{date.future}")
  private Date date;

  @NotNull(message = "{price.empty}")
  @DecimalMin(value = "0", inclusive = false, message = "{price.invalid}")
  private BigDecimal price;

 //getters and setters
}
```

### The client bean

```java
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;

public class ClientBean {
  @Autowired
  private Order order;

  @Autowired
  Validator validator;

  private void processOrder () {
      if (validateOrder()) {
          System.out.println("processing " + order);
      }
  }

  private boolean validateOrder () {
      Locale.setDefault(Locale.US);
      Set<ConstraintViolation<Order>> c = validator.validate(order);
      if (c.size() > 0) {
          System.out.println("Order validation errors:");
          c.stream().map(v -> v.getMessage())
           .forEach(System.out::println);
          return false;
      }
      return true;
  }
}
```

### Registering beans to Spring container

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Date;
import java.util.Locale;

 @Configuration
 public class Config {

    @Bean
    public ClientBean clientBean () {
        return new ClientBean();
    }

    @Bean
    public Order order () {
        Order order = new Order();
        //order.setPrice(BigDecimal.TEN);
        order.setDate(new Date(System.currentTimeMillis() - 60000));
        return order;
    }

    @Bean
    public Validator validatorFactory () {
        return new LocalValidatorFactoryBean();
    }
 }
```

The class `LocalValidatorFactoryBean` implements `javax.validation.Validator`, `javax.validation.ValidatorFactory` and `org.springframework.validation.Validator` interfaces. This class wraps standard JSR 349/303 bean validation bootstrapping process and also wraps the `javax.validation.Validator` access around `org.springframework.validation.Validator`. That also means in our above example, we could have injected `org.springframework.validation.Validator` instead of `javax.validation.Validator` and then in that case we will be using Spring validation API in `ClientBean#validateOrder()` method, just like we did in the first example.

Let's continue with `LocalValidatorFactoryBean` for this example and run the main method.


### The main method:

```java
 public static void main (String[] args) {
    AnnotationConfigApplicationContext context =
                        new AnnotationConfigApplicationContext(
                                            Config.class);

    ClientBean bean = context.getBean(ClientBean.class);
    bean.processOrder();
 }
```

### Output:

```Powershell
Order validation errors:
Price cannot be empty
Date must be in future
```

## Mixing the two approaches

Using JSR 349 annotations is the recommended way to perform bean validations. Although it's not always possible to use it for complex validation. Let's say in our above example if our `Order` bean has `customerId` filed as well and we want to validate that `customerId` against the ids from data service layer then annotation approach is not very suitable. In that case we can also mix the above two method of validations. Check out `ValidationMixedExample.java`.