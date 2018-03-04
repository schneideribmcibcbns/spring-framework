# @IfProfileValue Example

This example demonstrates how to use @IfProfileValue.

@IfProfileValue annotation is used on JUnit tests (doesn't work for others). When used on a method, which is also annotated with @Test, the method will be ignored given that: for a specified property key, the corresponding specified value (or one of the values; we can specify multiple values) does not match with any runtime Environment properties.

By default, it only works for Java system property but we can change that by implementing a custom ProfileValueSource (we will see that in the next tutorial). By default SystemProfileValueSource(an implementation of ProfileValueSource) is used which uses system properties as the underlying source.

When @IfProfileValue is used on the class level, all methods can be ignored based on the same condition.

## Creating a Spring application

```java
@Service
public class MyService {

  public String getMsg() {
      return "some msg";
  }
}
```

```java
@Configuration
@ComponentScan
public class AppConfig {
}
```

## The JUnit test

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class MyServiceTests {

  @Autowired
  private MyService myService;

  @Test
  @IfProfileValue(name = "os.name", values = {"Windows 10", "Windows 7"})
  public void testMyService() {
      String s = myService.getMsg();
      System.out.println(s);
      Assert.assertEquals("some msg", s);
  }
}
```