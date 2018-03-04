# Setting log file by using logging.file and logging.path properties

By default Spring Boot does not output logs to any file. If we want to have logs written in a file (in addition to the console output) then we should use either of `logging.file` or `logging.path` properties (not both).

Let's understand what rules are applied with examples:

1. `logging.file=my-file.txt` This will write logs to `my-file.txt` at the location where application is running (the working directory). Within an application, the working directory can be found by `System.getProperty("user.dir")`.

2. `logging.file=/my-folder/my-file.txt` In this case, the location will be the root folder in the current partition. For example if we are running in windows and application is running somewhere in the directory D: then the log file will be in `D:\my-folder\my-file.txt`. In Linux, it will be created under the root directory where only root user has the permission (sudo commands applied). In both cases, the user who is running the application should have the permission to create files, otherwise no log files will be created. In windows, we can also use paths like `D:/my-folder/my-file.txt` or `D:\\my-folder\\my-file.txt` (not recommended).

3. `logging.path=/my-folder/` It will write logs to `/myfolder/spring.log` in the root directory.
If we use both `logging.path` and `logging.file` then `logging.path` property will be ignored.

## Example

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off
logging.file=/my-logs/app.log
#logging.path=/my-logs/ will create spring.log
```

```
@SpringBootApplication
public class ExampleMain {
  private static final Logger logger = LoggerFactory.getLogger(ExampleMain.class);

  public static void main(String[] args) throws InterruptedException {
      System.out.println("Current Directory = " + System.getProperty("user.dir"));
      SpringApplication.run(ExampleMain.class, args);
      logger.info("just a test info log");
  }
}
```

**Output**

Running application via spring-boot plugin:

```
$ gradle bootRun
:compileJava UP-TO-DATE
:processResources
:classes
:findMainClass
:bootRun
Current Directory = C:\Users\Philip\repos\spring-framework\05-boot\12-logging\07-setting-log-file
2018-03-04 02:04:45.761  INFO 8328 --- [           main] com.logicbig.example.ExampleMain         : Starting ExampleMain on Joanna-VAIO with PID 8328 (C:\Users\Philip\repos\spring-framework\05-boot\12-logging\07-setting-log-file\build\classes\java\main started by Philip in C:\Users\Philip\repos\spring-framework\05-boot\12-logging\07-setting-log-file)
2018-03-04 02:04:45.765  INFO 8328 --- [           main] com.logicbig.example.ExampleMain         : No active profile set, falling back to default profiles: default
2018-03-04 02:04:45.855  INFO 8328 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@57f23557: startup date [Sun Mar 04 02:04:45 EST 2018]; root of context hierarchy
2018-03-04 02:04:46.456  INFO 8328 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-03-04 02:04:46.474  INFO 8328 --- [           main] com.logicbig.example.ExampleMain         : Started ExampleMain in 1.032 seconds (JVM running for 1.37)
2018-03-04 02:04:46.475  INFO 8328 --- [           main] com.logicbig.example.ExampleMain         : just a test info log
2018-03-04 02:04:46.475  INFO 8328 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@57f23557: startup date [Sun Mar 04 02:04:45 EST 2018]; root of context hierarchy
2018-03-04 02:04:46.476  INFO 8328 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown

BUILD SUCCESSFUL in 6s
4 actionable tasks: 3 executed, 1 up-to-date
```

The log file is written at following location:

```
$ cd /c/my-logs/

MINGW64 /c/my-logs
$ ls -l
total 4
-rw-r--r-- 1 pheely 197121 3093 Mar  4 02:04 app.log
```

```
$ cat app.log
2018-03-04 02:03:43.388  INFO 8740 --- [main] com.logicbig.example.ExampleMain         : Starting ExampleMain on Joanna-VAIO with PID 8740 (C:\Users\Philip\repos\spring-framework\05-boot\12-logging\07-setting-log-file\build\classes\java\main started by Philip in C:\Users\Philip\repos\spring-framework\05-boot\12-logging\07-setting-log-file)
2018-03-04 02:03:43.421  INFO 8740 --- [main] com.logicbig.example.ExampleMain         : No active profile set, falling back to default profiles: default
2018-03-04 02:03:43.793  INFO 8740 --- [main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@73d4cc9e: startup date [Sun Mar 04 02:03:43 EST 2018]; root of context hierarchy
2018-03-04 02:03:46.029  INFO 8740 --- [main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-03-04 02:03:46.060  INFO 8740 --- [main] com.logicbig.example.ExampleMain         : Started ExampleMain in 3.521 seconds (JVM running for 3.952)
2018-03-04 02:03:46.061  INFO 8740 --- [main] com.logicbig.example.ExampleMain         : just a test info log
2018-03-04 02:03:46.063  INFO 8740 --- [Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@73d4cc9e: startup date [Sun Mar 04 02:03:43 EST 2018]; root of context hierarchy
2018-03-04 02:03:46.065  INFO 8740 --- [Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
2018-03-04 02:04:45.761  INFO 8328 --- [main] com.logicbig.example.ExampleMain         : Starting ExampleMain on Joanna-VAIO with PID 8328 (C:\Users\Philip\repos\spring-framework\05-boot\12-logging\07-setting-log-file\build\classes\java\main started by Philip in C:\Users\Philip\repos\spring-framework\05-boot\12-logging\07-setting-log-file)
2018-03-04 02:04:45.765  INFO 8328 --- [main] com.logicbig.example.ExampleMain         : No active profile set, falling back to default profiles: default
2018-03-04 02:04:45.855  INFO 8328 --- [main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@57f23557: startup date [Sun Mar 04 02:04:45 EST 2018]; root of context hierarchy
2018-03-04 02:04:46.456  INFO 8328 --- [main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-03-04 02:04:46.474  INFO 8328 --- [main] com.logicbig.example.ExampleMain         : Started ExampleMain in 1.032 seconds (JVM running for 1.37)
2018-03-04 02:04:46.475  INFO 8328 --- [main] com.logicbig.example.ExampleMain         : just a test info log
2018-03-04 02:04:46.475  INFO 8328 --- [Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@57f23557: startup date [Sun Mar 04 02:04:45 EST 2018]; root of context hierarchy
2018-03-04 02:04:46.476  INFO 8328 --- [Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
```
