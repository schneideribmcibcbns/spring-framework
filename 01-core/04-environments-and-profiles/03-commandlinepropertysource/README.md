# Accessing application arguments with CommandLinePropertySource

CommandLinePropertySource is PropertySource subclass which is backed by command line arguments passed to a Java Application.

In this tutorial, we will see the examples of using the two CommandLinePropertySource implementations: SimpleCommandLinePropertySource and JOptCommandLinePropertySource.

## Using SimpleCommandLinePropertySource without Spring context

```java
public class CmdSourceExample1 {

  public static void main(String[] args) {
      SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);
      Arrays.stream(ps.getPropertyNames()).forEach(s ->
              System.out.printf("%s => %s%n", s, ps.getProperty(s)));
  }
}
```

Run the java application with two arguments: --myProp=testval1 --myProp2=testVal2

**Output**

```shell
myProp => testval1
myProp2 => testVal2
```

## Using SimpleCommandLinePropertySource with Spring context

```java
@Configuration
public class CmdSourceExample2 {

    @Bean
    public MyBean myBean1(){
        return new MyBean();
    }
    public static void main(String[] args) {

        PropertySource theSource = new SimpleCommandLinePropertySource(args);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CmdSourceExample2.class);

        context.getEnvironment().getPropertySources().addFirst(theSource);

        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }


    public class MyBean {
        @Autowired
        private Environment environment;

        public void doSomething() {
            String value = environment.getProperty("myProp");
            System.out.println("the value of myProp: " + value);
        }
    }
}
```

**Output**

```shell
the value of myProp: testval1
```

## Using SimpleCommandLinePropertySource and @Value annotation

```java
@Configuration
public class CmdSourceExample3 {

    @Bean
    public MyBean myBean(){
        return new MyBean();
    }

    public static void main(String[] args) {

        PropertySource theSource = new SimpleCommandLinePropertySource(args);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.getEnvironment().getPropertySources().addFirst(theSource);

        context.register(CmdSourceExample3.class);
        context.refresh();

        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }

    public class MyBean {
        @Value("${myProp}")
        private String myPropValue;

        public void doSomething() {
            System.out.println("the value of myProp: " + myPropValue);
        }
    }
}
```

**Output**

```shell
the value of myProp: testval1
```

## Using JOptCommandLinePropertySource

JOptCommandLinePropertySource is backed by OptionSet (part of a third party library, JOpt Simple, which supports various unix style options/arguments). To see what it can do, let's see a simple example first (we have already included maven dependency of JOpt simple in project browser below):

```java
public class CmdSourceExample4 {

    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.accepts("myProp").withRequiredArg();
        OptionSet options = parser.parse(args);

        boolean myProp = options.hasArgument("myProp");
        System.out.println(myProp);
        Object myProp1 = options.valueOf("myProp");
        System.out.println(myProp1);
    }
}
```

**Output**

```shell
true
testval1
```

Let's use it with JOptCommandLinePropertySource:


```java
@Configuration
public class CmdSourceExample5 {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.accepts("myProp").withRequiredArg();
        OptionSet options = parser.parse(args);

        PropertySource ps = new JOptCommandLinePropertySource(options);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.getEnvironment().getPropertySources().addFirst(ps);

        context.register(CmdSourceExample5.class);
        context.refresh();

        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }

    public class MyBean {
        @Value("${myProp}")
        private String myPropValue;

        public void doSomething() {
            System.out.println("the value of myProp: " + myPropValue);
        }
    }
}
```

**Output**

```shell
the value of myProp: testval1
```