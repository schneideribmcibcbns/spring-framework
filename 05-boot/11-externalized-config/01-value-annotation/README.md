# Using @Value annotation in Spring Boot

To use @Value annotation (in Spring Boot Application), we don't have to use @PropertySource annotation as we saw in [this spring core tutorial](../../../01-core/04-environments-and-profiles/02-properties/README.md). That's because Spring Boot by default reads properties from application.properties file (given that it is in classpath) or we have many other options as described [here](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files).

In this quick example, we will see how to use @Value in Spring Boot.

**src/main/resources/application.properties**

```shell
app.title=My Spring Application
```

**Main Class**

```java
@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(ExampleMain.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.startApplication();
    }

    private static class MyBean {

        @Value("${app.title}")
        private String appTitle;

        public void startApplication() {
            System.out.printf("-- running application: %s --%n", appTitle);

        }
    }
}
```