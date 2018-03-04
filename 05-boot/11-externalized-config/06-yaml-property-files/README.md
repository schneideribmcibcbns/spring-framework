# Using YAML property files

Spring boot supports YAML format for storing external properties. We can create `application.yml` as an alternative to `application.properties`. Or we can even use both of them in the same application.

**src/main/resources/application.yml**

```shell
app:
 title: Boot ${app} @project.artifactId@
spring:
    main:
     LogStartupInfo: false
```

In above file, we are specifying `app.title` and `spring.main.logStartupInfo` properties. We are also using `${}` and `@...@` placeholders. (check out the [last tutorial](../05-placeholders/README.md))

Let's create `application.property` as well:

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off
```
 
**The Main class**

```java
@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication bootApp = new SpringApplication(ExampleMain.class);
        ConfigurableApplicationContext context = bootApp.run(args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class MyBean {

        @Value("${app.title}")
        private String appTitle;

        public void doSomething() {
            System.out.printf("App title : %s%n", appTitle);
        }
    }
}
```

**Output**

```shell
2018-03-04 17:35:44.981  INFO 9084 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@12b0404f: startup date [Sun Mar 04 17:35:44 EST 2018]; root of context hierarchy
App title : Boot ymlProperties @project.artifactId@
2018-03-04 17:35:45.151  INFO 9084 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@12b0404f: startup date [Sun Mar 04 17:35:44 EST 2018]; root of context hierarchy
```