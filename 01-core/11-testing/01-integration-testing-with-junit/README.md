# Integration Testing with JUnit

As spring beans are nothing but POJOs, we can always write unit test for individual beans that simply instantiated using the new operator, without Spring or any other container. But that's not good enough if we also would like to test whether beans are wired together properly or not and whether everything works as expected when tested in Spring container.

The Spring TestContext Framework supports full integration with JUnit 4 through a custom runner.

By annotating test classes with `@RunWith(SpringJUnit4ClassRunner.class)` or the shorter `@RunWith(SpringRunner.class)` variant, we can write standard JUnit 4 unit and integration tests. That means we can have full support of spring context loading and dependency injection of the beans in our tests. Let's see how to do that with an example

## Example

### Creating a simple Spring application

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShoppingCart {
  @Autowired
  private OrderService orderService;
  private List<Order> orders = new ArrayList<>();

  public void addItem(String name, int qty) {
      orders.add(new Order(name, qty));
  }

  public String checkout() {
      String msg = orderService.placeOrders(orders);
      orders.clear();
      return msg;
  }
}
```

```java
@Service
public class OrderService {

  public String placeOrders(List<Order> orders) {
      //just a dummy service
      return orders.size() + " orders placed";
  }
}
```

```java
@Configuration
@ComponentScan("com.logicbig.example")
public class AppConfig {
}
```

### Writing JUnit test

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ShoppingCartTest {

  @Autowired
  private ShoppingCart shoppingCart;

  @Test
  public void testCheckout() {
      shoppingCart.addItem("Item1", 3);
      shoppingCart.addItem("item2", 5);
      String result = shoppingCart.checkout();
      Assert.assertEquals("2 orders placed", result);
  }
}
```

### What is @ContextConfiguration?

This class-level annotation is used to specify what application configuration to load for Spring context. In above example, we specified 'classes' element with the value of our JavaConfig class. It has other useful elements as well, which we will be exploring in coming tutorials.