# Accessing application arguments

In Spring boot application, there are two ways we can access application arguments:

1. By injecting `ApplicationArguments`. `ApplicationArguments` provides access to the arguments that were used to run a SpringApplication.

2. Spring boot implicitly registers `CommandLinePropertySource` with the Spring `Environment`. That means, we can use `@Value` annotation without any extra configuration.

## Injecting ApplicationArguments

```java
@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExampleMain.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class MyBean {
        @Autowired
        ApplicationArguments appArgs;

        public void doSomething() {
            List<String> args = appArgs.getOptionValues("myArg");
            if (args.size() > 0) {
                System.out.printf("The value of application arg myArg: %s%n", args.get(0));
            }
        }
    }
}
```

## By using @Value annotation

Here, we don't have to explicitly add `CommandLinePropertySource` to the `Environment object` (like we have to do with Spring core). Secondly our bean will be free from any Spring specific API.

```java
@SpringBootConfiguration
public class ExampleMain2 {

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExampleMain2.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class MyBean {
        @Value("${myArg}")
        private String myArgStr;

        public void doSomething() {
            System.out.printf("The value of application arg myArg: %s%n", myArgStr);
        }
    }
}
```
