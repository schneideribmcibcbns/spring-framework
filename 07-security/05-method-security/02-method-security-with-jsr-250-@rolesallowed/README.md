# Method Security with JSR-250 @RolesAllowed

Spring Security provides support for JSR-250 annotation security. That means we can use `javax.annotation.security.RolesAllowed` in the place of Spring's `@Secured` annotation.

We are going to reuse our [last example](../01-securing-service-layer-methods-with-@secured-annotation/README.md). We just need to replace `@Secured` with `@RolesAllowed` in the service class and enabled JSR-250 annotation in Java config class. 

## Additional Gradle Dependency

**build.gradle**

```gradle
	compile('javax.annotation:jsr250-api:1.0')
```

## Service Interface

```java
package com.logicbig.example;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface ShoppingCartService {
  @RolesAllowed("ROLE_CUSTOMER")
  int placeOrder(OrderItem order);

  @RolesAllowed("ROLE_ADMIN")
  List<OrderItem> getOrderList();
}
```

## Java Config class

```java
@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class AppConfig extends WebSecurityConfigurerAdapter {

  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .anyRequest().authenticated()
          .and()
          .formLogin()
          .and()
          .exceptionHandling().accessDeniedPage("/noAccess");
  }

  @Override
  public void configure(AuthenticationManagerBuilder builder)
          throws Exception {
      builder.inMemoryAuthentication()
             .withUser("ann").password("123").roles("CUSTOMER")
             .and()
             .withUser("ray").password("234").roles("ADMIN");
  }

  @Bean
  public ViewResolver viewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/views/");
      viewResolver.setSuffix(".jsp");
      return viewResolver;
  }
}
```