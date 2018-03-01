# Registering Servlet Components as Spring Beans

We saw in the last tutorial that Servlet 3.0 annotation can be scanned by using  @ServletComponentScan. Sometimes, however, that will not be possible to register Servlet Components by using Servlet 3.0 annotations and we would instead like to register them to the container programmatically. For example a library Servlet (or any component) is needed to be registered but we cannot modify the source code to add @WebServlet annotation.

Rather than working directly with servlet API like ServletContext#addServlet, Spring provides following classes to register servlet components as beans.

* ServletRegistrationBean
* FilterRegistrationBean
* ServletListenerRegistrationBean

By registering above beans with Spring context, will automatically register the underlying Servlet component to the Servlet Container. The Servlet component as a bean will also have all advantages which a Spring bean does, for example we can inject configuration or dependencies into them.

We are going to modify our last tutorial example to demonstrate how to use above registration bean classes.

## A Servlet component

```java
public class MyServlet extends HttpServlet {

  @Override
  protected void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

      PrintWriter writer = resp.getWriter();
      writer.println("response from servlet ");
  }
}
```

## Registering Servlet using ServletRegistrationBean

```java
@SpringBootApplication
public class Main {
    .............
  @Bean
  ServletRegistrationBean myServletRegistration () {
      ServletRegistrationBean srb = new ServletRegistrationBean();
      srb.setServlet(new MyServlet());
      srb.setUrlMappings(Arrays.asList("/path2/*"));
      return srb;
  }
    .............
}
```

## A Filter component

```java
public class MyFilter implements Filter {
  
  @Override
  public void init (FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter (ServletRequest request,
                        ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
      String url = request instanceof HttpServletRequest ?
                ((HttpServletRequest) request).getRequestURL().toString() : "N/A";
      System.out.println("from filter, processing url: " + url);
      chain.doFilter(request, response);
  }

  @Override
  public void destroy () {
  }
}
```

## Registering Filter using FilterRegistrationBean

```java
@SpringBootApplication
public class Main {
    .............
  @Bean
  FilterRegistrationBean myFilterRegistration () {
      FilterRegistrationBean frb = new FilterRegistrationBean();
      frb.setFilter(new MyFilter());
      frb.setUrlPatterns(Arrays.asList("/*"));
      return frb;
  }
    .............
}
```

## A Filter component

```java
public class MyServletListener implements ServletContextListener {
  
  @Override
  public void contextInitialized (ServletContextEvent sce) {
      System.out.println("from ServletContextListener: " +
                                   " context initialized");
  }

  @Override
  public void contextDestroyed (ServletContextEvent sce) {
  }
}
```

## Registering Web Listener using ServletListenerRegistrationBean

```java
@SpringBootApplication
public class Main {
    .............
  @Bean
  ServletListenerRegistrationBean<ServletContextListener> myServletListener () {
      ServletListenerRegistrationBean<ServletContextListener> srb =
                new ServletListenerRegistrationBean<>();
      srb.setListener(new MyServletListener());
      return srb;
  }
}
```

## A Spring Controller

```java
@Controller
public class MyController {

  @RequestMapping("/*")
  @ResponseBody
  public String handler () {
      return "response form spring controller method";
  }
}
```

The output of this example is exactly same as the last example.