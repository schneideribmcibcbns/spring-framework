# Understanding SpringServletContainerInitializer

The class SpringServletContainerInitializer implements ServletContainerInitializer. That means this class will be loaded and its onStartup() method will be invoked by the Servlet container (version 3.0+) during startup given that spring-web module JAR is present on the classpath.

## WebApplicationInitializer

SpringServletContainerInitializer is annotated with @HandlesTypes(WebApplicationInitializer.class). That means, its onStartup() method is passed with all classes (on classpath) implementing WebApplicationInitializer. Spring will initialize all such concrete classes and will call their WebApplicationInitializer#onStartup(servletContext) method. These classes are free to do any programmatic servlet component registration and initialization in their onStartup() method.

The client code can directly implement WebApplicationInitializer and can register DispatcherServlet as we saw in getting started example.

## Spring's abstract WebApplicationInitializer implementations

Spring also provides abstracts implementations of this interface: AbstractDispatcherServletInitializer and AbstractAnnotationConfigDispatcherServletInitializer which already register DispatcherServlet. The client implementation of one of these abstract classes, can further customize the registration process.

AbstractAnnotationConfigDispatcherServletInitializer also initializes AnnotationConfigWebApplicationContext. The client code needs to provide client side configuration classes. Following snippet shows how to implement its abstract methods:

```java 
public class AppInitializer extends
          AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses () {
        return new Class<?>[]{MyAppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses () {
        return null;
    }

    @Override
    protected String[] getServletMappings () {
        return new String[]{"/"};
    }
}
```
