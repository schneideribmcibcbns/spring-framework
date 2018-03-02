# Meta Annotations

Generally in Java, an annotation is termed as meta-annotation if it is used on another annotation. You have probably seen such annotations many times. For example: @Document, @Inherited etc. There's no specific declaration needed for an annotation to become a meta-annotation, i.e. any annotation which has declared its @Target with ElementType.TYPE can be meta-annotated on other annotation definitions.

Spring provides many such annotations, for example @RequestMapping variants. The main purpose of using such meta-annotation in Spring is to group/compose multiple annotations together to ease the configuration meta-data on the developer side.

## Creating a custom annotation meta-annotated with other annotations

We can create our own annotations which can be annotated with Spring meta-annotations, without providing any custom annotation-processor for that. Spring implicitly recognizes meta-annotations and delegates the processing to the existing related processors.

In this simple example we are going to create annotation 'ResourceGone' which will be meta-annotated with @RequestMapping, @ResponseStatus(HttpStatus.GONE) and @ResponseBody. The purpose of this annotation would be to avoid repeating same set of annotations on multiple controller methods whenever a resource does not exist anymore.

### Creating the annotation

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.GET)
@ResponseStatus(HttpStatus.GONE)
@ResponseBody
public @interface ResourceGone {
  
  @AliasFor(annotation = RequestMapping.class)
  String[] value () default {};
}
```

### The controller

```java
@Controller
public class TheController {

  @ResourceGone(value = "/link1")
  public String handle1 () {
      return "The resource 'link1' doesn't exist anymore";
  }
  
  @ResourceGone(value = "/link2")
  public String handle2 () {
      return "The resource 'link2' doesn't exist anymore";
  }
}
```

### Spring boot main class

```java
@SpringBootApplication
public class Main {

  public static void main (String[] args) {
      SpringApplication.run(Main.class, args);
  }
}
```

### Output

![module](images/output1.png)

Confirming status code 410 (gone) in HTTPie

![module](images/output2.png)