# @ConfigurationProperties Cascaded Validation by using @Valid

In the last example, we saw how to use JSR 303/349 validation annotations with @ConfigurationProperties. In following example, we will see how to validate nested (cascaded) configuration properties by using @Valid annotation.

**The external properties - src/main/resources/application.properties**

```shell
spring.main.banner-mode=off 
spring.main.logStartupInfo=false
app.main-screen-properties.refresh-rate=15
app.main-screen-properties.width=10
app.main-screen-properties.height=400
app.admin-contact-number.value=111-111-111
app.admin-contact-number.type=personal
```

Properties Bean

```java
@ConfigurationProperties class
@Component
@ConfigurationProperties("app")
@Validated
public class MyAppProperties {
  @NotNull
  @Valid
  private MainScreenProperties mainScreenProperties;

  @NotNull
  @Valid
  private PhoneNumber adminContactNumber;
    .............
}
```

```java
public class PhoneNumber {
  @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
  private String value;
  @Pattern(regexp = "(?i)cell|house|work")
  private String type;
    .............
}
```

```java
public class MainScreenProperties {
  @Min(1)
  private int refreshRate;
  @Min(50)
  @Max(1000)
  private int width;
  @Min(50)
  @Max(600)
  private int height;
    .............
}
```

We purposely added such values in the external properties file, which should fail per above validation annotations to see whether or not the cascaded validation is performed as expected.

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
2018-03-04 18:45:49.990  INFO 9652 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@7fe8ea47: startup date [Sun Mar 04 18:45:49 EST 2018]; root of context hierarchy
2018-03-04 18:45:50.321  WARN 9652 --- [           main] o.h.v.m.ParameterMessageInterpolator     : HV000184: ParameterMessageInterpolator has been chosen, EL interpolation will not be supported
2018-03-04 18:45:50.435 ERROR 9652 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Properties configuration failed validation
2018-03-04 18:45:50.435 ERROR 9652 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Field error in object 'app' on field 'adminContactNumber.value': rejected value [111-111-111]; codes [Pattern.app.adminContactNumber.value,Pattern.adminContactNumber.value,Pattern.value,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber.value,adminContactNumber.value]; arguments []; default message [adminContactNumber.value],[Ljavax.validation.constraints.Pattern$Flag;@1f010bf0,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@40db2a24]; default message [must match "\d{3}-\d{3}-\d{4}"]
2018-03-04 18:45:50.435 ERROR 9652 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Field error in object 'app' on field 'mainScreenProperties.width': rejected value [10]; codes [Min.app.mainScreenProperties.width,Min.mainScreenProperties.width,Min.width,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.mainScreenProperties.width,mainScreenProperties.width]; arguments []; default message [mainScreenProperties.width],50]; default message [must be greater than or equal to 50]
2018-03-04 18:45:50.435 ERROR 9652 --- [           main] o.s.b.b.PropertiesConfigurationFactory   : Field error in object 'app' on field 'adminContactNumber.type': rejected value [personal]; codes [Pattern.app.adminContactNumber.type,Pattern.adminContactNumber.type,Pattern.type,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber.type,adminContactNumber.type]; arguments []; default message [adminContactNumber.type],[Ljavax.validation.constraints.Pattern$Flag;@1f010bf0,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@10cf09e8]; default message [must match "(?i)cell|house|work"]
2018-03-04 18:45:50.436  WARN 9652 --- [           main] s.c.a.AnnotationConfigApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myAppProperties': Could not bind properties to MyAppProperties (prefix=app, ignoreInvalidFields=false, ignoreUnknownFields=true, ignoreNestedProperties=false); nested exception is org.springframework.validation.BindException: org.springframework.boot.bind.RelaxedDataBinder$RelaxedBeanPropertyBindingResult: 3 errors
Field error in object 'app' on field 'adminContactNumber.value': rejected value [111-111-111]; codes [Pattern.app.adminContactNumber.value,Pattern.adminContactNumber.value,Pattern.value,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber.value,adminContactNumber.value]; arguments []; default message [adminContactNumber.value],[Ljavax.validation.constraints.Pattern$Flag;@1f010bf0,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@40db2a24]; default message [must match "\d{3}-\d{3}-\d{4}"]
Field error in object 'app' on field 'mainScreenProperties.width': rejected value [10]; codes [Min.app.mainScreenProperties.width,Min.mainScreenProperties.width,Min.width,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.mainScreenProperties.width,mainScreenProperties.width]; arguments []; default message [mainScreenProperties.width],50]; default message [must be greater than or equal to 50]
Field error in object 'app' on field 'adminContactNumber.type': rejected value [personal]; codes [Pattern.app.adminContactNumber.type,Pattern.adminContactNumber.type,Pattern.type,Pattern.java.lang.String,Pattern]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [app.adminContactNumber.type,adminContactNumber.type]; arguments []; default message [adminContactNumber.type],[Ljavax.validation.constraints.Pattern$Flag;@1f010bf0,org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute@10cf09e8]; default message [must match "(?i)cell|house|work"]
2018-03-04 18:45:50.442  INFO 9652 --- [           main] utoConfigurationReportLoggingInitializer : 

Error starting ApplicationContext. To display the auto-configuration report re-run your application with 'debug' enabled.
2018-03-04 18:45:50.446 ERROR 9652 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

Binding to target MyAppProperties{
mainScreenProperties=MainScreenProperties{refreshRate=15, width=10, height=400}, adminContactNumber=PhoneNumber{value='111-111-111', type='personal'}
} failed:

    Property: app.adminContactNumber.value
    Value: 111-111-111
    Reason: must match "\d{3}-\d{3}-\d{4}"

    Property: app.mainScreenProperties.width
    Value: 10
    Reason: must be greater than or equal to 50

    Property: app.adminContactNumber.type
    Value: personal
    Reason: must match "(?i)cell|house|work"


Action:

Update your application's configuration
```