# @TestPropertySource default properties file detection

This example demonstrates how to use an implicit properties location for @TestPropertySource. If @TestPropertySource is declared as an empty annotation (i.e., without explicit values for the locations or properties attributes), an attempt will be made to detect a default properties file (on the classpath) relative to the class that declares the annotation.

## Creating a Spring application

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

```shell
theManager@example.com
```

## The default properties file

**src/test/resources/com/logicbig/example/ReportServiceTests.properties**

```shell
report-subscriber=theTester@example.com
```

## The JUnit test

We are not going to specify 'locations' or 'properties' attribute with @TestPropertySource:

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource
public class ReportServiceTests {
  @Autowired
  private ReportService reportService;

  @Test
  public void testReportSubscriber() {
      String s = reportService.getReportSubscriber();
      System.out.println(s);
      Assert.assertEquals("theTester@example.com", s);
  }
}
```