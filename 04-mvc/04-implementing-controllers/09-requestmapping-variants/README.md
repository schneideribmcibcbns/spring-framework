# Composed @RequestMapping Variants

Spring 4.3 introduced new annotations which serve the same purpose as @RequestMapping having predefined 'method' (HTTP verb) value. Those annotations are actually themselves meta-annotated with @RequestMapping with the related value of 'method' element.

Followings are those annotations:

* @GetMapping
* @PostMapping
* @PutMapping
* @DeleteMapping
* @PatchMapping

To understand clearly how Spring is using Java annotation to achieve that, let's see the definition of @GetMapping:

```java
package org.springframework.web.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.GET)
public @interface GetMapping {

	@AliasFor(annotation = RequestMapping.class)
	String name() default "";

	@AliasFor(annotation = RequestMapping.class)
	String[] value() default {};

	@AliasFor(annotation = RequestMapping.class)
	String[] path() default {};

	@AliasFor(annotation = RequestMapping.class)
	String[] params() default {};

	@AliasFor(annotation = RequestMapping.class)
	String[] headers() default {};

	@AliasFor(annotation = RequestMapping.class)
	String[] produces() default {};
}
```

As we can see all elements of @RequestMapping are redefined here, except for 'method' element which is hard-coded (in above case, method = RequestMethod.GET) with @RequestMapping defined as type level annotation.

Though this is just Spring internal annotation processing details but out of curiosity, just imagine, Spring annotation processor would be delegating the processing for these new annotations to old ones along with the hard-coded method value.

Also notice 'consumes' element is not defined for @GetMapping (as compare to @RequestMapping) as no data is consumed in the Get request handler, but it does for POST, PUT and DELETE requests. The advantage of these kind of approaches is, it reduces the configuration metadata on the application side and the code is more readable.

## Example

### The Controller

Let's create a simple controller with handler method having each of the new mapping annotation as listed above:

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {

    @GetMapping("test")
    public String handleGetRequest () {

        return "GetMapping-view";
    }

    @PostMapping("test")
    public String handlePostRequest () {

        return "PostMapping-view";
    }

    @PutMapping("test")
    public String handlePutRequest () {

        return "PutMapping-view";
    }

    @DeleteMapping("test")
    public String handleDeleteRequest () {

        return "DeleteMapping-view";
    }

    @PatchMapping("test")
    public String handlePatchRequest () {

        return "PatchMapping-view";
    }

}
```

### The JUnit Test

```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MyWebConfig.class)
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testGet () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("GetMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.get("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }

    @Test
    public void testPost () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("PostMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.post("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }

    @Test
    public void testPut () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("PutMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.put("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }

    @Test
    public void testDelete () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("DeleteMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.delete("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }

    @Test
    public void testPatch () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("PatchMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.patch("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }
}
```

### Test Output

All 5 tests passed.