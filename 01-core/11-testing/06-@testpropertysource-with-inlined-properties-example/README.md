# @TestPropertySource with inlined properties example

This example demonstrates how to use 'properties' attribute of @TestPropertySource to specify inlined properties. The inlined properties will override properties coming from the sources loaded via 'locations' attribute (if specified any).

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
@TestPropertySource(locations = "classpath:test.properties",
      properties = "report-subscriber=tester@example.com")
public class ReportServiceTests {
  @Autowired
  private ReportService reportService;

  @Test
  public void testReportSubscriber() {
      String s = reportService.getReportSubscriber();
      System.out.println(s);
      Assert.assertEquals("tester@example.com", s);
  }
}
```