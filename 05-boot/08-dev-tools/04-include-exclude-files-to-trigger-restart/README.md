# Include/Exclude files to trigger restart.

Spring provides `spring.devtools.restart.exclude` property to exclude files to trigger a restart. By default this property is assigned to following values:

```
spring.devtools.restart.exclude=META-INF/maven/**,META-INF/resources/**,resources/**,static/**,public/**,templates/**,**/*Test.class,**/*Tests.class,git.properties
```

It's always a good idea to check these properties in Spring boot's `DevToolsProperties.java` as the values might change from version to version.

By default static/public files are also excluded to trigger a restart.

Note that, the excluded files do not trigger a restart but they might still get updated on the browser by LiveReload.

In this tutorial we will also use the following restart delay properties (which got nothing to do with above property):

```
spring.devtools.restart.poll-interval=1000 #Amount of time (in milliseconds) to wait between polling for classpath changes.
spring.devtools.restart.quiet-period=400 #Amount of quiet time (in milliseconds) required without any classpath changes before a restart is triggered.
```

The values shown are the default ones. By using smaller values, we can reduce restart delay. Also `quiet-period` must be exclusively less than `poll-interval`.

## Example

**src/main/resources/application.properties**

```
spring.devtools.restart.poll-interval=10
spring.devtools.restart.quiet-period=5
spring.devtools.restart.exclude=public/myPage2.html
```

**src/main/resources/public/myPage1.html**

```
<html>
  <body>
    <h1>static page 1</h1>
  </body>
</html>
```

**src/main/resources/public/myPage2.html**

```
<html>
  <body>
    <h1>static page 2</h1>
  </body>
</html>
```

```java
@SpringBootApplication
public class ExampleMain extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
      return builder.sources(ExampleMain.class);
  }

  public static void main(String[] arg) {
      SpringApplication.run(ExampleMain.class, arg);
  }
}
```

## Run the application

Changing `public/myPage1.html` file will trigger the restart of the application. Changes made on `public/myPage2.html` will reflect on the browser, but will not trigger the restart.

## Excluding additional paths

If we don't want to override the default value of `spring.devtools.restart.exclude`, then we can use following property to exclude additional paths:

```
spring.devtools.restart.additional-exclude
```

By default no value is assigned to this property.