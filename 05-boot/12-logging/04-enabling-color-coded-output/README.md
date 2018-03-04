# Spring Boot - Enabling Color Coded Output

Spring boot can display ANSI color coded output if the target terminal supports it. We need to set `spring.output.ansi.enabled` property. The possible values are `ALWAYS`, `DETECT` and `NEVER`.

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off 
spring.output.ansi.enabled=ALWAYS
```

Note that the property `spring.output.ansi.enabled` is set to `DETECT` value by default, but not all terminals will show the colored output unless we set it to `ALWAYS`. Also if we set the value `ALWAYS` and if the target terminal does not support ANSI codes then instead of colored output, the raw embedded color codes will be shown in the logs.