# @ActiveProfiles Example

This example demonstrates how to use @ActiveProfiles.

@ActiveProfiles is a class-level annotation that is used to specify which profile should be selected when loading an ApplicationContext for test classes.

## Example

### Creating a Spring Profile based application

```java
@Configuration
public class AppConfig {

  @Bean
  @Profile(PROFILE_LOCAL)
  public DataService dataServiceLocal() {
      return DataServiceLocal.Instance;
  }

  @Bean
  @Profile(PROFILE_REMOTE)
  public DataService dataServiceRemote() {
      return DataServiceRemote.Instance;
  }

  public static final String PROFILE_LOCAL = "local";
  public static final String PROFILE_REMOTE = "remote";
}
```

```java
@Service
public interface DataService {
  List<Customer> getCustomersByAge(int minAge, int maxAge);
}
```

```java
public class Customer {
  private long customerId;
  private String name;
  private int age;
    .............
}
```

```java
@Configuration
@ComponentScan("com.logicbig.example")
public class AppConfig {
}
```

### The JUnit test

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles(AppConfig.PROFILE_LOCAL)
public class DataServiceTests {

  @Autowired
  private DataService dataService;

  @Test
  public void testCustomerAges() {
      List<Customer> customers = dataService.getCustomersByAge(25, 40);
      for (Customer customer : customers) {
          int age = customer.getAge();
          Assert.assertTrue("Age range is not 25 to 40: " + customer,
                  age >= 25 && age < 40);
      }
  }
}
```
