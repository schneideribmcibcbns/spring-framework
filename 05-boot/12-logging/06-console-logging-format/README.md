# Customizing Console Logging Format

In this example, we will see how to customize console pattern by specifying logging.pattern.console property in application.properties file. The easy way to do that to copy paste the default pattern as specified in `DefaultLogbackConfiguration` class as `CONSOLE_LOG_PATTERN` constant and start modifying it. Following is the default pattern (spring-boot 1.5.8.RELEASE):

```shell
%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}
```

logging.pattern.console property only works if we use Logback logging implementation (the default). The pattern which is needed to be specified also follows the Logback layout rules as specified [here](https://logback.qos.ch/manual/layouts.html#ClassicPatternLayout).

Spring additionally provides some custom logback's Converter implementations to customize output, for example Spring binds the convention-word '%clr' to ColorConverter which returns output with the ANSI color codes. '%wEx' is another conversion word which binds to a converter which returns a custom exception output. Check out `DefaultLogbackConfiguration.base()` to learn how Spring does that.

Following is our customized console pattern:

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off 
spring.output.ansi.enabled=ALWAYS
logging.pattern.console=%clr(%d{yy-MM-dd E HH:mm:ss.SSS}){blue} %clr(%-5p) %clr(${PID}){faint} %clr(---){faint} %clr([%8.15t]){cyan} %clr(%-40.40logger{0}){blue} %clr(:){red} %clr(%m){faint}%n
```

Spring Boot also maps the different log levels to different ANSI color codes as specified [here](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-logging.html#boot-features-logging-color-coded-output); that only works if we don't specify our own %clr conversion-word value with %p, for example something like %clr(%5p){green}.

**Main class**

```java
@SpringBootApplication
public class ExampleMain {
  private static final Logger logger = LoggerFactory.getLogger(ExampleMain.class);
  public static void main(String[] args) throws InterruptedException {

      ConfigurableApplicationContext context =
              SpringApplication.run(ExampleMain.class, args);
      logger.warn("warning msg");
      logger.error("error msg");
  }
}
```