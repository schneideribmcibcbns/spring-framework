# Initializing ConfigurableApplicationContext with ApplicationContextInitializer

ApplicationContextInitializer in general can be used for some programmatic initialization of the application context. For example, registering property sources or activating profiles with the context environment.

This example demonstrates how to use ApplicationContextInitializer with Spring tests by using 'initializers' element of @ContextConfiguration.

## Example

### Creating a simple Spring application

```java
@Configuration
public class AppConfig {

  @Bean
  @Profile("windows")
  public MyService myServiceA() {
      return new MyServiceA();
  }

  @Bean
  @Profile("other")
  public MyService myServiceB() {
      return new MyServiceB();
  }
}
```

```java
public interface MyService {
  public String doSomething();

}
```

```java
public class MyServiceA implements MyService {

  @Override
  public String doSomething() {
      return "in " + this.getClass().getSimpleName();
  }
}
```

```java
public class MyServiceB implements MyService {

  @Override
  public String doSomething() {
      return "in " + this.getClass().getSimpleName();
  }
}
```

### Implementing the Initializer

```java
public class MyApplicationContextInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext ac) {
      String os = System.getProperty("os.name");
      String profile = (os.toLowerCase().startsWith("windows")) ? "windows" : "other";
      ConfigurableEnvironment ce = ac.getEnvironment();
      ce.addActiveProfile(profile);
  }
}
```

### The JUnit test

Following test will fail if run other than Windows O.S.

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class,
      initializers = MyApplicationContextInitializer.class)
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
```