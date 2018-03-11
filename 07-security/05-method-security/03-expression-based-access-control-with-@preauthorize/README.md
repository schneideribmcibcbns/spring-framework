# Expression-Based Access Control with @PreAuthorize

Spring Security provides the ability to use Spring EL expressions as an authorization mechanism. We can use `@PreAuthorize` annotation and can specify method access-control expression as its attribute.

There are four annotations which support expression attributes to allow pre and post-invocation authorization checks around methods: `@PreAuthorize`, `@PreFilter`, `@PostAuthorize` and `@PostFilter`.

`@PreFilter` and `@PostFilter` annotations support filtering of submitted collection arguments or return values.

Following example shows the use of `@PreAuthorize`.

We are going to reuse our [@Secured annotation example](../01-securing-service-layer-methods-with-@secured-annotation/README.md). We just need to replace `@Secured` with `@PreAuthorize` annotation in the service class and enabled pre/post annotations in Java config class via `prePostEnabled = true`.

## Service Interface

```java
package com.logicbig.example;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ShoppingCartService {
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  int placeOrder(OrderItem order);

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  List<OrderItem> getOrderList();
}
```

[Here](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#el-common-built-in) is a list of the expressions which can be used with the pre/post annotations.

## Java Config class

```java
@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends WebSecurityConfigurerAdapter {
    .............
}
```

The output will be same as [this example](../01-securing-service-layer-methods-with-@secured-annotation/README.md).