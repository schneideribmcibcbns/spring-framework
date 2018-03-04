# @ConfigurationProperties Validation by using JSR-303/349 Annotations

In Spring Boot application, we can have @ConfigurationProperties classes validated via JSR-303/349 (Java EE Bean Validation specification) annotations. To make it work, we need to use Spring `@Validated` annotation on our configuration class. We also need to add a JSR-303/349 implementation in the classpath.

**Adding Hibernate validator dependency**

In this example, we are going to use Hibernate validator (which is the reference implementation of JSR-303/349), so we have to add the related maven dependency:

**pom.xml**

```xml
<dependency>
   <groupId>org.hibernate</groupId>
   <artifactId>hibernate-validator</artifactId>
</dependency>
```

Note that, we did not add the hibernate-validator version, that's because it is already configured in spring-boot-dependencies pom (which is the parent of spring-boot-starter-parent).

**The external properties**

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off 
spring.main.logStartupInfo=false
app.admin-contact-number=111-111-111
app.refresh-rate=0
```

**@ConfigurationProperties class**

```java
@Component
@ConfigurationProperties("app")
@Validated
public class MyAppProperties {
  @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
  private String adminContactNumber;
  @Min(1)
  private int refreshRate;
    .............
}
```

Note that, we have added `@Validated` annotation to turn on the validation.

Also, we purposely added such values in the external properties files, which should fail per above validation annotations. We are doing that to see whether or not the validation is performed as expected.

**The Main class**

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) throws InterruptedException {
      ConfigurableApplicationContext context = SpringApplication.run(ExampleMain.class, args);
      MyAppProperties bean = context.getBean(MyAppProperties.class);
      System.out.println(bean);
  }
}
```

**Output**

```log4j
2018-03-04 18:36:30.239  INFO 10088 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@7fe8ea47: startup date [Sun Mar 04 18:36:30 EST 2018]; root of context hierarchy
2018-03-04 18:36:30.539  WARN 10088 --- [           main] o.h.v.m.ParameterMessageInterpolator     : HV000184: ParameterMessageInterpolator has been chosen, EL interpolation will not be supported
2018-03-04 18:36:30.690 ERROR 10088 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Properties configuration failed validation
2018-03-04 18:36:30.690 ERROR 10088 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Field error in object 'app' on field 'refreshRate': rejected value [0]; codes [Min.app.refreshRate,Min.refreshRate,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.refreshRate,refreshRate]; arguments []; default message [refreshRate],1]; default message [must be greater than or equal to 1]
2018-03-04 18:36:30.690 ERROR 10088 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Field error in object 'app' on field 'adminContactNumber': rejected value [111-111-111]; codes [Pattern.app.adminContactNumber,Pattern.adminContactNumber,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber,adminContactNumber]; arguments []; default message [adminContactNumber],[Ljavax.validation.constraints.Pattern$Flag;@2c78d320,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@132e0cc]; default message [must match "\d{3}-\d{3}-\d{4}"]
2018-03-04 18:36:30.691  WARN 10088 --- [           main] s.c.a.AnnotationConfigApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myAppProperties': Could not bind properties to MyAppProperties (prefix=app, ignoreInvalidFields=false, ignoreUnknownFields=true, ignoreNestedProperties=false); nested exception is org.springframework.validation.BindException: org.springframework.boot.bind.RelaxedDataBinder$RelaxedBeanPropertyBindingResult: 2 errors
Field error in object 'app' on field 'refreshRate': rejected value [0]; codes [Min.app.refreshRate,Min.refreshRate,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.refreshRate,refreshRate]; arguments []; default message [refreshRate],1]; default message [must be greater than or equal to 1]
Field error in object 'app' on field 'adminContactNumber': rejected value [111-111-111]; codes [Pattern.app.adminContactNumber,Pattern.adminContactNumber,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber,adminContactNumber]; arguments []; default message [adminContactNumber],[Ljavax.validation.constraints.Pattern$Flag;@2c78d320,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@132e0cc]; default message [must match "\d{3}-\d{3}-\d{4}"]
2018-03-04 18:36:30.697  INFO 10088 --- [           main] utoConfigurationReportLoggingInitializer : 

Error starting ApplicationContext. To display the auto-configuration report re-run your application with 'debug' enabled.
2018-03-04 18:36:30.701 ERROR 10088 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

Binding to target MyAppProperties{adminContactNumber='111-111-111', refreshRate=0} failed:

    Property: app.refreshRate
    Value: 0
    Reason: must be greater than or equal to 1

    Property: app.adminContactNumber
    Value: 111-111-111
    Reason: must match "\d{3}-\d{3}-\d{4}"


Action:

Update your application's configuration

2018-03-04 18:36:30.239  INFO 10088 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@7fe8ea47: startup date [Sun Mar 04 18:36:30 EST 2018]; root of context hierarchy
2018-03-04 18:36:30.539  WARN 10088 --- [           main] o.h.v.m.ParameterMessageInterpolator     : HV000184: ParameterMessageInterpolator has been chosen, EL interpolation will not be supported
2018-03-04 18:36:30.690 ERROR 10088 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Properties configuration failed validation
2018-03-04 18:36:30.690 ERROR 10088 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Field error in object 'app' on field 'refreshRate': rejected value [0]; codes [Min.app.refreshRate,Min.refreshRate,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.refreshRate,refreshRate]; arguments []; default message [refreshRate],1]; default message [must be greater than or equal to 1]
2018-03-04 18:36:30.690 ERROR 10088 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Field error in object 'app' on field 'adminContactNumber': rejected value [111-111-111]; codes [Pattern.app.adminContactNumber,Pattern.adminContactNumber,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber,adminContactNumber]; arguments []; default message [adminContactNumber],[Ljavax.validation.constraints.Pattern$Flag;@2c78d320,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@132e0cc]; default message [must match "\d{3}-\d{3}-\d{4}"]
2018-03-04 18:36:30.691  WARN 10088 --- [           main] s.c.a.AnnotationConfigApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myAppProperties': Could not bind properties to MyAppProperties (prefix=app, ignoreInvalidFields=false, ignoreUnknownFields=true, ignoreNestedProperties=false); nested exception is org.springframework.validation.BindException: org.springframework.boot.bind.RelaxedDataBinder$RelaxedBeanPropertyBindingResult: 2 errors
Field error in object 'app' on field 'refreshRate': rejected value [0]; codes [Min.app.refreshRate,Min.refreshRate,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.refreshRate,refreshRate]; arguments []; default message [refreshRate],1]; default message [must be greater than or equal to 1]
Field error in object 'app' on field 'adminContactNumber': rejected value [111-111-111]; codes [Pattern.app.adminContactNumber,Pattern.adminContactNumber,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber,adminContactNumber]; arguments []; default message [adminContactNumber],[Ljavax.validation.constraints.Pattern$Flag;@2c78d320,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@132e0cc]; default message [must match "\d{3}-\d{3}-\d{4}"]
2018-03-04 18:36:30.697  INFO 10088 --- [           main] utoConfigurationReportLoggingInitializer : 

Error starting ApplicationContext. To display the auto-configuration report re-run your application with 'debug' enabled.
2018-03-04 18:36:30.701 ERROR 10088 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

Binding to target MyAppProperties{adminContactNumber='111-111-111', refreshRate=0} failed:

    Property: app.refreshRate
    Value: 0
    Reason: must be greater than or equal to 1

    Property: app.adminContactNumber
    Value: 111-111-111
    Reason: must match "\d{3}-\d{3}-\d{4}"


Action:

Update your application's configuration

```