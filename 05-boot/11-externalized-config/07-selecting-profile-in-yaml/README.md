# Selecting Profile configuration in YAML property file

From YAML property files, Spring Boot can select different set of properties which belong to a specific profile, given that if the multiple YAML documents contain `spring.profiles` keys assigned to a profile name.

According to YAML specification, multiple YAML documents can be added in a single file separated by '---' (check out [this tutorials](https://www.logicbig.com/tutorials/misc/yaml/snake-yaml-loading.html) as well).

**src/main/resources/application.yml**

```shell
refresh:
   rate: 5
---
spring:
   profiles: dev, test
refresh:
   rate: 10
---
spring:
   profiles: prod
refresh:
   rate: 8
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
        ConfigurableApplicationContext context = SpringApplication.run(ExampleMain.class, args);

        String[] profiles = context.getEnvironment().getActiveProfiles();
        System.out.println("Active Profiles: "+ Arrays.toString(profiles));

        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class MyBean {

        @Value("${refresh.rate}")
        private int refreshRate;

        public void doSomething() {
            System.out.printf("Refresh Rate : %s%n", refreshRate);
        }
    }
}
```

**Output**

```shell
2018-03-04 17:46:23.819  INFO 4696 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@6e0dec4a: startup date [Sun Mar 04 17:46:23 EST 2018]; root of context hierarchy
Active Profiles: []
Refresh Rate : 5
2018-03-04 17:46:24.023  INFO 4696 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@6e0dec4a: startup date [Sun Mar 04 17:46:23 EST 2018]; root of context hierarchy
```

In above example, we didn't specify any profile during startup, so default value of 'refresh.rate' was used.

Let's select profile 'dev' by specifying the value for `spring.profiles.active` property:

**Output**

```shell
2018-03-04 17:47:28.437  INFO 4180 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@96def03: startup date [Sun Mar 04 17:47:28 EST 2018]; root of context hierarchy
Active Profiles: [dev]
Refresh Rate : 10
2018-03-04 17:47:28.592  INFO 4180 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@96def03: startup date [Sun Mar 04 17:47:28 EST 2018]; root of context hierarchy
```