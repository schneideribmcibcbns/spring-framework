# Context Configuration Inheritance

@ContextConfiguration annotation has 'inheritLocations' and 'inheritInitializers' elements which specify whether configuration locations (config classes/resources) and context initializers declared by superclasses should be inherited. By default both flags are true. That means a test subclass inherits both the config locations as well as the context initializers declared by any superclasses. Let's understand that with examples.

## Using default i.e. inheritLocations=true and inheritInitializers=true

### A Spring application

```java
@Service
public interface CustomerService {
  String getCustomerById(String id);
}
```

```java
@Service
public interface OrderService {
  String getOrdersForCustomer(String customerId);
}
```

```java
@Configuration
public class AppConfig {
  @Bean
  public CustomerService customerService() {
      return new CustomerService() {
          @Override
          public String getCustomerById(String id) {
              return "Customer " + id;
          }
      };
  }
}
```

```java
@Configuration
public class AppConfigExtended {
  @Bean
  public OrderService orderService() {
      return new OrderService() {
          @Override
          public String getOrdersForCustomer(String customerId) {
              return "orders for customer " + customerId;
          }
      };
  }
}
```

## The Test classes

```java
@ContextConfiguration(classes = AppConfig.class,
      initializers = MyContextInitializer.class)
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
```

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfigExtended.class)
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
```

```java
public class MyContextInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext ac) {
      System.out.println("-- in context initializer --");
  }
}

```

## Turning off inheritLocations

With the same above Spring application, let's don't inherit config locations (the config class) from the super class by setting inheritLocations=false. We will also provide an alternative configuration which will replace the super class's one.

```java
@ContextConfiguration(inheritLocations = false,
      classes = {
              AppConfigExtended.class, OtherAppConfig.class
      })
```

```java
public class ExtendedServiceTests extends BaseServiceTests {

  @Autowired
  private OrderService orderService;
    .............
}
```

```java
@Configuration
public class OtherAppConfig {
  @Bean
  public CustomerService customerService() {
      return new CustomerService() {
          @Override
          public String getCustomerById(String id) {
              return "Other Customer " + id;
          }
      };
  }
}
```