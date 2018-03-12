# Message codes created by MessageCodesResolver and their resolution to error messages

In Spring, when we call methods like `Errors#rejectValue()` (`ValidationUtils` methods), the underlying implementation of `Errors` will not only register the provided code, but also a number of additional codes. `MessageCodesResolver` (an interface) is responsible to build these message codes. By default `DefaultMessageCodesResolver` implementation is used. In following examples, we will understand the way these additional codes are created and will map them to error messages.

## An Object to validate

```java
public class Order {
  private LocalDate orderDate;
  private BigDecimal orderPrice;
    .............
}
```

## Validator implementation

```java
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
```

## Performing validations

In following example, we are printing the all codes associated with each error object:

```java
@Configuration
public class ExampleMain {

  @Bean
  OrderValidator validator() {
      return new OrderValidator();
  }

  public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(ExampleMain.class);

      Order order = new Order();
      order.setOrderPrice(BigDecimal.ZERO);

      BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(order, Order.class.getName());
      OrderValidator validator = context.getBean(OrderValidator.class);
      ValidationUtils.invokeValidator(validator, order, bindingResult);
      bindingResult.getAllErrors().forEach(e -> System.out.println(Arrays.toString(e.getCodes())));
  }
}
```

## Output

```shell
[date.empty.com.logicbig.example.Order.orderDate, date.empty.orderDate, date.empty.java.time.LocalDate, date.empty]
[price.invalid.com.logicbig.example.Order.orderPrice, price.invalid.orderPrice, price.invalid.java.math.BigDecimal, price.invalid]
```

The additional codes are appended with a combination of fully qualified object/field names to our provided code (the ones we provided as the second argument of `ValidationUtils.reject` methods in our validator class). Check out [doc](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/validation/DefaultMessageCodesResolver.html) for a detailed description of how different combination of these codes are generated.

We can map one of above codes (for each error) to the message strings in our resource file.

## Customizing DefaultMessageCodesResolver

By default `DefaultMessageCodesResolver` uses `Format.PREFIX_ERROR_CODE` which appends the user provided code (for example, 'orderDate' in above code) at the beginning of the generated message codes. Let's change that to `Format.POSTFIX_ERROR_CODE`:

```
@Configuration
public class ExampleMain2 {

  @Bean
  OrderValidator validator(){
      return new OrderValidator();
  }

  public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(ExampleMain2.class);

      Order order = new Order();
      order.setOrderPrice(BigDecimal.ZERO);
      BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(order, Order.class.getName());
      DefaultMessageCodesResolver messageCodesResolver =
              (DefaultMessageCodesResolver) bindingResult.getMessageCodesResolver();
      messageCodesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
      OrderValidator validator = context.getBean(OrderValidator.class);
      ValidationUtils.invokeValidator(validator, order, bindingResult);
      bindingResult.getAllErrors().forEach(e -> System.out.println(Arrays.toString(e.getCodes())));
  }
}
```

**Output**

```shell
[com.logicbig.example.Order.orderDate.date.empty, orderDate.date.empty, java.time.LocalDate.date.empty, date.empty]
[com.logicbig.example.Order.orderPrice.price.invalid, orderPrice.price.invalid, java.math.BigDecimal.price.invalid, price.invalid]
```

## Resolving codes to messages

Since all implementations of `ApplicationContext` implement `ResourceLoader`, we can use it to get resource messages:

```java
@Configuration
public class ExampleMain3 {

  @Bean
  OrderValidator validator(){
      return new OrderValidator();
  }

  public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(ExampleMain3.class);

      Order order = new Order();
      order.setOrderPrice(BigDecimal.ZERO);
      BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(order, Order.class.getName());
      DefaultMessageCodesResolver messageCodesResolver =
              (DefaultMessageCodesResolver) bindingResult.getMessageCodesResolver();
      messageCodesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
      OrderValidator validator = context.getBean(OrderValidator.class);
      ValidationUtils.invokeValidator(validator, order, bindingResult);
      bindingResult.getAllErrors().forEach(e -> System.out.println(context.getMessage(e, Locale.US)));
  }
}
```

**Output**

```shell
Date cannot be empty
Price must be greater than 0
```

In above example, since we did not provide any resource bundles, the default error messages (which we provided as last argument of `ValidationUtils.reject` methods) were printed.

Let's provide a properties file containing codes to message mappings:

**src/main/resources/ValidationMessages_en.properties**

```shell
orderDate.date.empty=Order Date must be non-empty.
orderPrice.price.empty=Order Price must be non-empty.
orderPrice.price.invalid=Order Price must be zero or less.
```

```java
@Configuration
public class ExampleMain4 {

  @Bean
  OrderValidator validator(){
      return new OrderValidator();
  }

  @Bean
  public MessageSource messageSource () {
      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
      messageSource.setBasename("ValidationMessages");
      return messageSource;
  }

  public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(ExampleMain4.class);

      Order order = new Order();
      order.setOrderPrice(BigDecimal.ZERO);
      BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(order, Order.class.getName());
      DefaultMessageCodesResolver messageCodesResolver =
              (DefaultMessageCodesResolver) bindingResult.getMessageCodesResolver();
      messageCodesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
      OrderValidator validator = context.getBean(OrderValidator.class);
      ValidationUtils.invokeValidator(validator, order, bindingResult);
      bindingResult.getAllErrors().forEach(e -> System.out.println(context.getMessage(e, Locale.US)));
  }
}
```

**Output**

```shell
Order Date must be non-empty.
Order Price must be zero or less.
```