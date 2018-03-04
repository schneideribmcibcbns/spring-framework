Using ${} placeholders in Property Files

Spring provides it's own variable substitution in property files. We just need to use ${someProp} in property file and start the application having 'someProp' in system properties or as main class (or jar) argument '--someProp=theValue'.

This feature allows us to use 'short' command line arguments.

**src/main/resources/application.properties**

```shell
app.title=Boot ${app}
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
        SpringApplication bootApp = new SpringApplication(ExampleMain.class);
        bootApp.setBannerMode(Banner.Mode.OFF);
        bootApp.setLogStartupInfo(false);
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

In Eclipse run configuration, specify --app="a cool app" as program argument.

**Output**

```shell
2018-03-04 17:13:13.337  INFO 8040 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@28c4711c: startup date [Sun Mar 04 17:13:13 EST 2018]; root of context hierarchy
App title : Boot a cool app
2018-03-04 17:13:13.516  INFO 8040 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@28c4711c: startup date [Sun Mar 04 17:13:13 EST 2018]; root of context hierarchy
```

Or specify -Dapp=fantastic as a VM argument.

**Output**

```shell
2018-03-04 17:19:07.322  INFO 9144 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@28c4711c: startup date [Sun Mar 04 17:19:07 EST 2018]; root of context hierarchy
App title : Boot fantastic
2018-03-04 17:19:07.487  INFO 9144 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@28c4711c: startup date [Sun Mar 04 17:19:07 EST 2018]; root of context hierarchy
```
