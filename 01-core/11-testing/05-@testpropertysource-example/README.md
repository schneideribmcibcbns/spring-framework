# @TestPropertySource Example

This example demonstrates how to use @TestPropertySource. It is a class-level annotation that is used to specify which properties files should be loaded when running the test class.

Test property sources have the highest precedence than all other properties sources. That means Test source will override all other properties.

## Example

### Creating a Spring application

```java
@Service
public class ReportService {
  @Value("${report-subscriber:admin@example.com}")
  private String theSubscriber;

  public String getReportSubscriber() {
      return theSubscriber;
  }
}
```

**src/main/resources/prod.properties**

```shell
report-subscriber=theManager@example.com
```

**src/main/resources/test.properties**

```shell
report-subscriber=theDeveloper@example.com
```

```java
@PropertySource("classpath:prod.properties")
@Configuration
@ComponentScan
public class AppConfig {
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
      return new PropertySourcesPlaceholderConfigurer();
  }

  public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);
      ReportService rs = context.getBean(ReportService.class);
      System.out.println(rs.getReportSubscriber());
  }
}
```

Running above class:

**Output**

```shell
theManager@example.com
```

### The JUnit test

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource("classpath:test.properties")
public class ReportServiceTests {
  @Autowired
  private ReportService reportService;

  @Test
  public void testReportSubscriber() {
      String s = reportService.getReportSubscriber();
      System.out.println(s);
      Assert.assertEquals("theDeveloper@example.com", s);
  }
}
```