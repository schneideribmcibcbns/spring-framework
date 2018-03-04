# Resolving active profiles programmatically by using ActiveProfilesResolver

ActiveProfilesResolver interface can be implemented to programmatically select profiles for Spring Tests. We can register our ActiveProfilesResolver implementation with the 'resolver' attribute of @ActiveProfiles annotation. Let's see an example how to do that.

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

### Implementing MyActiveProfilesResolver

```java
public class MyActiveProfilesResolver  implements ActiveProfilesResolver{
  @Override
  public String[] resolve(Class<?> testClass) {
      String os = System.getProperty("os.name");
      String profile = (os.toLowerCase().startsWith("windows")) ? "windows" : "other";
      return new String[]{profile};
  }
}
```

### The JUnit test

Following test will fail if run other than Windows O.S.

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles(resolver = MyActiveProfilesResolver.class)
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
