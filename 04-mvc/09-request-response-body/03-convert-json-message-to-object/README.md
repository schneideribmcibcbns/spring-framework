# Convert JSON Message to Object using @RequestBody and @ResponseBody

Spring provides support for JSON content-type to backing object conversion by using `MappingJackson2HttpMessageConverter`. This message converter won't get activated unless we add jackson-databind jar in the classpath.

Let's see how we can do that with a step by step example.

## Add jackson-databind dependency

Add this dependency in build.gradle

```
apply plugin: 'war'
apply plugin: 'eclipse-wtp'

repositories {
    mavenCentral()
}

war {
	baseName = 'myapp'
}

eclipse {
	wtp {
		component {
			contextPath = 'spring-mvc'
		}
	}
}

dependencies {
    compile("org.springframework:spring-webmvc:5.0.1.RELEASE")
    compile("javax.servlet:javax.servlet-api:3.1.0")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.4")
    
    testCompile("junit:junit:4.12")
    testCompile("org.springframework:spring-test:5.0.1.RELEASE")
}
```

## Create Backing Object

```
public class User implements Serializable {
    private Long id;
    private String name;
    private String password;
    private String emailAddress;

    //getters and setters
}
```

##Create Controller

```
@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST,
                        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void handleJsonPostRequest (@RequestBody User user, Model model) {
        System.out.println("saving user: "+user);
        userService.saveUser(user);
    }

    @RequestMapping(method = RequestMethod.GET,
                        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<User> handleAllUserRequest () {
        return userService.getAllUsers();
    }
}
```

Note that unlike XML message conversion, we don't have to wrap the collection type into a new class.