# Spring JUnit 4 Rules

Spring 4.12 introduced two JUnit rules: `SpringClassRule` and `SpringMethodRule`.

Instead of using @RunWith(SpringRunner.class), we can use these two rules to achieve the same result.

As JUnit framework allows only one Runner in a test class, Spring JUnit rules provide the flexibility to run tests in Spring context environment without using @RunWith(SpringRunner.class), which allows us to use other JUnit runners like Parameterized in the same test class.

## Creating a simple Spring application

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShoppingCart {
  private List<String> orders = new ArrayList<>();

  public void addItem(String name, int qty) {
      orders.add(String.format("order. Item:%s qty%s", name, qty));
  }

  public String checkout() {
      String msg = placeOrders();
      orders.clear();
      return msg;
  }

  public String placeOrders() {
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

## Writing JUnit test

```java
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
```

**Note:**

SpringMethodRule should always be annotated with @Rule and SpringClassRule should always be annotated with @ClassRule. That's because SpringMethodRule supports instance level features, whereas, SpringClassRule supports class level features.

