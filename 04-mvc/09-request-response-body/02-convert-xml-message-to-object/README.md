# Convert XML Message to Object using @RequestBody and @ResponseBody

By default Spring provides support for XML content-type to backing object conversion by using `Jaxb2RootElementHttpMessageConverter`. We just need to use `@RequestBody` or `@ResponseBody` on the target type.

Let's see some examples.

## Create Backing Object

```java
package com.logicbig.example;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class User implements Serializable {
    private Long id;
    private String name;
    private String password;
    private String emailAddress;

    //getters and setters
}
```

Note that we have to use JAXB bind annotations `@XmlRootElement` for message conversion to work.

## Using @RequestBody in our Controller

```
@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void handleXMLPostRequest(@RequestBody User user) {
        System.out.println(user);
        userService.saveUser(user);
    }
}
```

## Using @ResponseBody in our Controller

In this example we are going to show how to return a List of users. 

JAXB requires to wrap the collection in a separate class to handle generic properly:

```
package com.logicbig.example;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class UserList {
    private List<User> users;
    //getter and setter
}
```

```
@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserList handleAllUserRequest () {
        UserList list = new UserList();
        list.setUsers(userService.getAllUsers());
        return list;
    }
}
```