# Understanding UserDetailsService and creating a custom one

`UserDetailsService` is used to load user-specific data. In previous examples, we have been using either in-memory authentication which uses `InMemoryUserDetailsManager` or JDBC authentication which uses `JdbcUserDetailsManager`. Both of which are implementations of `UserDetailsService`.

## The UserDetailsService interface

```java
package org.springframework.security.core.userdetails;
 ...
public interface UserDetailsService {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```

The method `loadUserByUsername()` locates the user by the `username`. Spring internally uses the returned non-null `UserDetails` object to verify the password and roles against the client's entered values.

## AuthenticationProvider vs UserDetailsService

`UserDetailsService` is not an alternative to `AuthenticationProvider` but it is used for a different purpose i.e. to load user details. Typically, an `AuthenticationProvider` implementation can use `UserDetailsService` instance to retrieve user details during its authentication process. For example, `DaoAuthenticationProvider`, in case of JDBC-authentication, uses `JdbcUserDetailsManager` as an implementation of `UserDetailsService`. In case of in-memory authentication, `DaoAuthenticationProvider` uses `InMemoryUserDetailsManager` implementation.

This example will demonstrate how to create and register a custom UserDetailsService.

## Implementing UserDetailsService

```java
public class MyUserDetailsService implements UserDetailsService {
    private static List<UserObject> users = new ArrayList();

    public MyUserDetailsService() {
        //in a real application, instead of using local data,
        // we will find user details by some other means e.g. from an external system
        users.add(new UserObject("erin", "123", "ADMIN"));
        users.add(new UserObject("mike", "234", "ADMIN"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserObject> user = users.stream()
                                         .filter(u -> u.name.equals(username))
                                         .findAny();
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return toUserDetails(user.get());
    }

    private UserDetails toUserDetails(UserObject userObject) {
        return User.withUsername(userObject.name)
                   .password(userObject.password)
                   .roles(userObject.role).build();
    }

    private static class UserObject {
        private String name;
        private String password;
        private String role;

        public UserObject(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }
    }
}
```

## Java Config class

```java
@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan
public class AppConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .antMatchers("/**").hasRole("ADMIN")
          .anyRequest()
          .authenticated()
          .and()
          .formLogin();
  }

  @Override
  public void configure(AuthenticationManagerBuilder builder)
          throws Exception {
      builder.userDetailsService(new MyUserDetailsService());
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

We can also register our `UserDetailsService` as a bean by using `@Bean` or `@Component`, as an alternative to above `AuthenticationManagerBuilder` configuration.

Also **note** that with above configuration, `DaoAuthenticationProvider` is used as an `AuthenticationProvider`.

## The Controller

```java
@Controller
public class MyController {

  @RequestMapping("/**")
  public String handleRequest(HttpServletRequest request, Model model) {
      Authentication auth = SecurityContextHolder.getContext()
                                                 .getAuthentication();
      model.addAttribute("uri", request.getRequestURI())
           .addAttribute("user", auth.getName())
           .addAttribute("roles", auth.getAuthorities());
      return "my-page";
  }
}
```

## The JSP page

**src/main/webapp/WEB-INF/views/my-page.jsp**

```jsp
<html lang="en">
<body>
 <p>URI: ${uri} <br/>
 User :  ${user} <br/>
 roles:  ${roles} <br/><br/>
 </p>
 <form action="/logout" method="post">
     <input type="hidden"
            name="${_csrf.parameterName}"
            value="${_csrf.token}"/>
  <input type="submit" value="Logout">
</form>
</body>
</html>
```

## Output

Same as the [previous example](../01-authenticationprovider/README.md) 