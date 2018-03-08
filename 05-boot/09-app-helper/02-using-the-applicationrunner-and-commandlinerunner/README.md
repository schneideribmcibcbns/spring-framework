# Using the ApplicationRunner and CommandLineRunner

If we need to get a callback at the SpringApplication startup, we can implement the `ApplicationRunner` or `CommandLineRunner` interfaces and register them as beans. Let's see how we can do that with examples.

## Implementing CommandLineRunner

```java
@SpringBootConfiguration
public class CmdExample {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    CommandLineRunner cmdRunner() {
        return new CmdRunner();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CmdExample.class, args);
        System.out.println("Context ready : " + context);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class CmdRunner implements CommandLineRunner {

        @Override
        public void run(String... strings) throws Exception {
            System.out.println("running CmdRunner#run: " + Arrays.toString(strings));
        }
    }

    private static class MyBean {

        public void doSomething() {
            System.out.println("In a bean doing something");
        }
    }
}
```

## Implementing ApplicationRunner

```java
@SpringBootConfiguration
public class AppExample {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    ApplicationRunner appRunner() {
        return new AppRunner();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AppExample.class, args);
        System.out.println("Context ready : " + context);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class AppRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("running appRunner#run: " + Arrays.toString(args.getSourceArgs()));
        }
    }

    private static class MyBean {

        public void doSomething() {
            System.out.println("In a bean doing something");
        }
    }
}
```

## ApplicationRunner vs CommandLineRunner

Technically there's no difference between the two, both are called at the end of `SpringApplication#run`. There's only one difference: `ApplicationRunner#run` is called with `ApplicationArguments` instead of `String[] args`.

Multiple `ApplicationRunner`/`CommandLineRunner` beans can be registered within the same application context and can be ordered using the `Ordered` interface or `@Order` annotation.
