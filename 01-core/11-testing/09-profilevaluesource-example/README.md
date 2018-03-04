# ProfileValueSource Example

This example demonstrates how to use ProfileValueSource and @ProfileValueSourceConfiguration to specify a custom strategy for retrieving profile values for a given testing environment.

Rather than using the default SystemProfileValueSource (check out the [last tutorial](../08-@ifprofilevalue-example/README.md)), a custom implementation of ProfileValueSource can be configured on a test class by using @ProfileValueSourceConfiguration. When @IfProfileValue is used in the same class, it's value is tested against the value returned from `ProfileValueSource#get(String key)` to enable or disable the corresponding test. Let's understand that with an example.

## Creating a Simple Spring application

```java
@Service
public class ReportService {

  public String getReport() {
      return "some report";
  }
}
```

```java
@Configuration
@ComponentScan
public class AppConfig {

}
```

## Implementing ProfileValueSource

This implementation first attempts to retrieve the value for the provided 'key' from Properties loaded from a property file. If not present (if null) then tries the System properties for the same key.

```java
public class MyProfileValueSource implements ProfileValueSource {
  private final Properties testProps;

  public MyProfileValueSource() {

      ClassPathResource resource = new ClassPathResource("test.properties");
      if (resource.exists()) {
          try {
              this.testProps = PropertiesLoaderUtils.loadProperties(resource);
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }else{
          testProps = new Properties();
      }
  }

  @Override
  public String get(String key) {
      return testProps.getProperty(key, System.getProperty(key));
  }
}
```

**src/main/resources/test.properties**

```shell
report.enabled=false
```

## The JUnit test

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ProfileValueSourceConfiguration(MyProfileValueSource.class)
public class ServiceTests {

  @Autowired
  private ReportService reportService;

  @Test
  @IfProfileValue(name = "report.enabled", value = "true")
  public void testReport() {
      String s = reportService.getReport();
      System.out.println(s);
      Assert.assertEquals("some report", s);
  }
}
```

The test was skipped. We can enable the method by setting report.enabled=true in the property file without changing the code.