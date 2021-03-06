# Destruction callback

SpringApplication implicitly registers a shutdown hook with the JVM to ensure that ApplicationContext is closed gracefully on exit. That will also call all bean methods annotated with `@PreDestroy`. That means we don't have to explicitly use `ConfigurableApplicationContext#registerShutdown Hook()` in a boot application, like we have to do in spring core application.

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

        //no need to call context.registerShutdownHook();
    }

    private static class MyBean {

        @PostConstruct
        public void init() {
            System.out.println("init");
        }

        public void doSomething() {
            System.out.println("in doSomething()");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("destroy");
        }
    }
}
```
