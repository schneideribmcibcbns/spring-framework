# Application exit code

Spring boot provides various ways to return a custom exit code on application exit. In this tutorials, we will go through each of them.

## Implementing ExitCodeGenerator

Spring beans can implement `org.springframework.boot.ExitCodeGenerator` interface and return exit code from its `getExitCode` method. We must also explicitly call `SpringApplication.exit(..)` method as shown in the following example;

```java
@SpringBootConfiguration
public class ExampleExitCodeGenerator {

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExampleExitCodeGenerator.class, args);

        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();

        int exitValue = SpringApplication.exit(context);
        System.exit(exitValue);
    }

    private static class MyBean implements ExitCodeGenerator {

        public void doSomething() {
            System.out.println("in doSomething()");
        }

        @Override
        public int getExitCode() {
            return 500;
        }
    }
}
```

### Output

```apache
in doSomething()

Process finished with exit code 500
```

## Listening to ExitCodeEvent

Spring boot fires `ExitCodeEvent` on finding application specific exit codes. We can listen to that event as shown in this example. 

```java
@SpringBootConfiguration
public class ExampleExitCodeEvent {

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    MyBean2 myBean2() {
        return new MyBean2();
    }

    public static void main(String[] args) {
        ApplicationContext context =
                SpringApplication.run(ExampleExitCodeEvent.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();

        int exit = SpringApplication.exit(context);
        System.exit(exit);
    }

    private static class MyBean implements ExitCodeGenerator {

        public void doSomething() {
            System.out.println("in doSomething()");
        }

        @Override
        public int getExitCode() {
            return 100;
        }
    }

    private static class MyBean2 {
        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("-- ExitCodeEvent --");
            System.out.println("exit code: " + event.getExitCode());
        }
    }
}
```

### Output

```
in doSomething()
-- ExitCodeEvent --
exit code: 100

Process finished with exit code 100
```

## Throwing a custom exception from run method of ApplicationRunner/CommandLineRunner

The custom exception class must implement `ExitCodeGenerator` interface. This only works if this exception is thrown in `ApplicationRunner`/`CommandLineRunner` run method.

Here, we don't have to explicitly call `SpringApplication.exit(..)`

```java
@SpringBootConfiguration
public class ExampleExitCodeGeneratorException {
    @Bean
    ApplicationRunner appRunner() {
        return new MyAppRunner();
    }

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleExitCodeGeneratorException.class, args);
    }

    private static class MyAppRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("in ApplicationRunner#run method");
            throw new MyExitCodeException("test exception");
        }
    }

    private static class MyExitCodeException extends RuntimeException
            implements ExitCodeGenerator {

        public MyExitCodeException(String message) {
            super(message);
        }

        @Override
        public int getExitCode() {
            return 5;
        }
    }

    private static class MyBean {
        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("-- ExitCodeEvent --");
            System.out.println("exit code: " + event.getExitCode());
        }
    }
}
```

### Output

```
in ApplicationRunner#run method
-- ExitCodeEvent --
exit code: 5
2017-05-17 11:56:14.273 ERROR 12088 --- [xception.main()] o.s.boot.SpringApplication               : Application startup failed

java.lang.IllegalStateException: Failed to execute ApplicationRunner
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:770) [spring-boot-1.5.3.RELEASE.jar:1.5.3.RELEASE]
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:757) [spring-boot-1.5.3.RELEASE.jar:1.5.3.RELEASE]
	at org.springframework.boot.SpringApplication.afterRefresh(SpringApplication.java:747) [spring-boot-1.5.3.RELEASE.jar:1.5.3.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315) [spring-boot-1.5.3.RELEASE.jar:1.5.3.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1162) [spring-boot-1.5.3.RELEASE.jar:1.5.3.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1151) [spring-boot-1.5.3.RELEASE.jar:1.5.3.RELEASE]
	at com.logicbig.example.ExampleExitCodeGeneratorException.main(ExampleExitCodeGeneratorException.java:20) [classes/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_111]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_111]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_111]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_111]
	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:294) [exec-maven-plugin-1.5.0.jar:na]
	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_111]
Caused by: com.logicbig.example.ExampleExitCodeGeneratorException$MyExitCodeException: test exception
	at com.logicbig.example.ExampleExitCodeGeneratorException$MyAppRunner.run(ExampleExitCodeGeneratorException.java:28) ~[classes/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:767) [spring-boot-1.5.3.RELEASE.jar:1.5.3.RELEASE]
	... 12 common frames omitted

2017-05-17 11:56:14.273  INFO 12088 --- [xception.main()] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@349186e0: startup date [Wed May 17 11:56:14 CDT 2017]; root of context hierarchy
[WARNING] 
java.lang.reflect.InvocationTargetException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:294)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.IllegalStateException: Failed to execute ApplicationRunner
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:770)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:757)
	at org.springframework.boot.SpringApplication.afterRefresh(SpringApplication.java:747)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1162)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1151)
	at com.logicbig.example.ExampleExitCodeGeneratorException.main(ExampleExitCodeGeneratorException.java:20)
	... 6 more
Caused by: com.logicbig.example.ExampleExitCodeGeneratorException$MyExitCodeException: test exception
	at com.logicbig.example.ExampleExitCodeGeneratorException$MyAppRunner.run(ExampleExitCodeGeneratorException.java:28)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:767)
	... 12 more

Process finished with exit code 5
```

Note that, we are still able to receive ExitCodeEvent in above example.

## Using ExitCodeExceptionMapper

`ExitCodeExceptionMapper` is a strategy interface that can be used to provide a mapping between exceptions and exit codes as shown in the following example.

```java
@SpringBootConfiguration
public class ExampleExitCodeExceptionMapper {

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    ApplicationRunner appRunner() {
        return new MyAppRunner();
    }

    @Bean
    ExitCodeExceptionMapper exitCodeExceptionMapper() {
        return exception -> {

            if (exception.getCause() instanceof MyException) {
                return 10;
            }
            return 1;
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleExitCodeExceptionMapper.class, args);
    }

    private static class MyAppRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("in command line");
            if (true) throw new MyException("test");
        }
    }

    private static class MyBean {

        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("-- ExitCodeEvent --");
            System.out.println("exit code: " + event.getExitCode());
        }
    }

    private static class MyException extends RuntimeException {

        public MyException(String message) {
            super(message);
        }

    }
}
```

### Output

```
in command line
-- ExitCodeEvent --
exit code: 10
2018-03-08 01:12:14.751 ERROR 10060 --- [           main] o.s.boot.SpringApplication               : Application startup failed

java.lang.IllegalStateException: Failed to execute ApplicationRunner
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:726) [spring-boot-1.5.10.RELEASE.jar:1.5.10.RELEASE]
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:713) [spring-boot-1.5.10.RELEASE.jar:1.5.10.RELEASE]
	at org.springframework.boot.SpringApplication.afterRefresh(SpringApplication.java:703) [spring-boot-1.5.10.RELEASE.jar:1.5.10.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:304) [spring-boot-1.5.10.RELEASE.jar:1.5.10.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1118) [spring-boot-1.5.10.RELEASE.jar:1.5.10.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1107) [spring-boot-1.5.10.RELEASE.jar:1.5.10.RELEASE]
	at com.logicbig.example.ExampleExitCodeExceptionMapper.main(ExampleExitCodeExceptionMapper.java:32) [bin/:na]
Caused by: com.logicbig.example.ExampleExitCodeExceptionMapper$MyException: test
	at com.logicbig.example.ExampleExitCodeExceptionMapper$MyAppRunner.run(ExampleExitCodeExceptionMapper.java:40) ~[bin/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:723) [spring-boot-1.5.10.RELEASE.jar:1.5.10.RELEASE]
	... 6 common frames omitted
```
