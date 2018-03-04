# Understanding TestExecutionListener

A `TestExecutionListener` listens for the test execution events published by the `TestContextManager`.

## Default Test ExecutionListeners

The `spring-test` module declares all default TestExecutionListeners in its META-INF/spring.factories properties file.

**Spring.factories (spring-test version 4.3.10.RELEASE):**

```shell
# Default TestExecutionListeners for the Spring TestContext Framework
#
org.springframework.test.context.TestExecutionListener = \
	org.springframework.test.context.web.ServletTestExecutionListener,\
	org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener,\
	org.springframework.test.context.support.DependencyInjectionTestExecutionListener,\
	org.springframework.test.context.support.DirtiesContextTestExecutionListener,\
	org.springframework.test.context.transaction.TransactionalTestExecutionListener,\
	org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener
 .......
```

All above listeners have a specific responsibility in Spring integration tests execution. For example, `DependencyInjectionTestExecutionListener` provides support for dependency injection and initialization of test instances.

## What is spring.factories?

This is a general purpose factory loading mechanism provided by Spring core. It requires the file spring.factories (a properties file) located under META-INF.

`SpringFactoriesLoader.loadFactories(MyService.class, ..)` method can be used to load a specified class. This method returns list of specified class instances. There might be multiple spring.factories files in different JAR files in the class path which may defined a particular type multiple times.

Note that spring-boot also uses this mechanism to load auto configurations, check out [here](https://www.logicbig.com/tutorials/spring-framework/spring-boot/auto-config-mechanism.html).

## The TestExecutionListener interface

```java
package org.springframework.test.context;
    .......
public interface TestExecutionListener {
    void beforeTestClass(TestContext testContext) throws Exception;
    void prepareTestInstance(TestContext testContext) throws Exception;
    void beforeTestMethod(TestContext testContext) throws Exception;
    void afterTestMethod(TestContext testContext) throws Exception;
    void afterTestClass(TestContext testContext) throws Exception;
}
```

A `TestExecutionListener` implementation can receive events during different test execution stages. Let's see what we can do with `TestContext` which is passed to the listener on each event.

## The TextContext interface

```java
package org.springframework.test.context;
 ...
public interface TestContext extends AttributeAccessor, Serializable {
	ApplicationContext getApplicationContext();
	Class<?> getTestClass();
	Object getTestInstance();
	Method getTestMethod();
	Throwable getTestException();
	void markApplicationContextDirty(HierarchyMode hierarchyMode);
	void updateState(Object testInstance, Method testMethod, Throwable testException);
}
```

As seen above, TestContext contains information of spring context and of the target test class/methods as well, which can be used to alter the tests behavior or extend their functionality.

## Example of Custom TestExecutionListener

Let's see how to defined our own `TestExecutionListener` to understand how it works.

### Creating a simple Spring application

```java
@Component
public class MyBean {
  public void doSomething() {
      System.out.println("-- in MyBean.doSomething() method --");
  }
}
```

```java
@Configuration
@ComponentScan
public class AppConfig {
}
```

### Writing a TestExecutionListener

```java
public class MyListener implements TestExecutionListener {
  @Override
  public void beforeTestClass(TestContext testContext) throws Exception {
      System.out.println("MyListener.beforeTestClass()");
  }
	
  @Override
  public void prepareTestInstance(TestContext testContext) throws Exception {
      System.out.println("MyListener.prepareTestInstance()");

  }

  @Override
  public void beforeTestMethod(TestContext testContext) throws Exception {
      System.out.println("MyListener.beforeTestMethod()");
  }

  @Override
  public void afterTestMethod(TestContext testContext) throws Exception {
      System.out.println("MyListener.afterTestMethod()");
  }

  @Override
  public void afterTestClass(TestContext testContext) throws Exception {
      System.out.println("MyListener.afterTestClass");
  }
}
```

### Writing JUnit test

A TextExecutionListener can be registered via @TestExecutionListeners annotation:

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestExecutionListeners(value = {MyListener.class,
      DependencyInjectionTestExecutionListener.class})
public class MyTests {

  @Autowired
  private MyBean myBean;

  @Test
  public void testDoSomething() {
      myBean.doSomething();
  }
}
```

Note that, we also used `DependencyInjectionTestExecutionListener` in above example, that's because when we specify our own listeners with @TestExecutionListeners, all default listeners are overridden. In above example, We needed `DependencyInjectionTestExecutionListener` as we used @Autowired.

### Output

```shell
MyListener.beforeTestClass()
MyListener.prepareTestInstance()
Aug 21, 2017 11:07:29 AM org.springframework.context.support.GenericApplicationContext prepareRefresh
INFO: Refreshing org.springframework.context.support.GenericApplicationContext@735f7ae5: startup date [Mon Aug 21 11:07:29 CDT 2017]; root of context hierarchy
MyListener.beforeTestMethod()
-- in MyBean.doSomething() method --
MyListener.afterTestMethod()
MyListener.afterTestClass
```

## Merging TestExecutionListeners

As mentioned above, when we specify our own listeners with @TestExecutionListeners, all default listeners are overridden. One way to utilize default listeners is to include them manually in the value attribute of this annotation (as we saw above). Another way is to use 'mergeMode' of this annotation, which will not replace the default listeners. The following snippet shows the usage:

```java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestExecutionListeners(value = MyListener.class,
      mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class MyTests {
    ....
}
```

The default value of 'mergeMode' is `MergeMode.REPLACE_DEFAULTS`.