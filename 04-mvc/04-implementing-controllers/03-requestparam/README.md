# Binding URL Query Parameters with @RequestParam

The @RequestParam annotation is used to bind parameter values of query string to the controller method parameters.

## Using 'value' element of @RequestParam

'value' element of @RequestParam is used to specify URL query param name. Following handler method will be mapped with the request /employees?dept=IT.

```java
Controller
@RequestMapping("employees")
public class EmployeeController {

    @RequestMapping
    public String handleEmployeeRequestByDept (@RequestParam("dept") String deptName,
                                                                       Model map) {
        map.addAttribute("msg", "employees request by dept: " + deptName);
        return "my-page";
    }
}
```

The value of deptName in above snippet will be IT.

## @RequestParam without 'value' element

Just like @PathVariable, 'value' element of @RequestParam annotation can be skipped if the target variable name is same as param name. The code has to be compiled with debugging information (variable names must be included in the debugging information). Following handler will be mapped with /employees?sate=NC :

```java
@Controller
@RequestMapping("employees")
public class EmployeeController {

    @RequestMapping
    public String handleEmployeeRequestByArea (@RequestParam String state, Model map) {
        map.addAttribute("msg", "employees request by area: " + state);
        return "my-page";
    }
}
```

The value of state parameter will be NC.

## Using Multiple @RequestParam Annotations

A method can have any number of @RequestParam annotations. Following will be mapped with /employees?dept=IT&state=NC :

```java
@Controller
@RequestMapping("employees")
public class EmployeeController {

    @RequestMapping
    public String handleEmployeeRequestByDept (@RequestParam("dept") String deptName,
                                            @RequestParam("state") String stateCode,
                                            Model map) {
        map.addAttribute("msg", "employees request by dept and state code : "+
                                                           deptName+", "+stateCode);
        return "my-page";
    }
}
```

## Using Map with @RequestParam for Multiple Params

If the method parameter is Map<String, String> or MultiValueMap<String, String> then the map is populated with all query string names and values. Following will be mapped with /employees/234/messages?sendBy=mgr&date=20160210

```java
@Controller
@RequestMapping("employees")
public class EmployeeController {

    @RequestMapping("{id}/messages")
    public String handleEmployeeMessagesRequest (@PathVariable("id") String employeeId,
                                            @RequestParam Map<String, String> queryMap,
                                            Model model) {
        model.addAttribute("msg", "employee request by id and query map : "+
                  employeeId+", "+queryMap.toString());
        return "my-page";
    }
}
```

Where employeeId = "234" and queryMap = {sendBy=mgr, date=20160210}

## Auto Type Conversion

If target method parameter is not String, automatic type conversion may happen. All simple types such as int, long, Date, etc. are supported by default. Following will be mapped with /employees/234/paystubsByMonths?months=5:

```java
@Controller
@RequestMapping("employees")
public class EmployeeController{

    @RequestMapping("{id}/paystubsByMonths")
    public String handleRequest4 (@PathVariable("id") String employeeId,
                                  @RequestParam("months") int previousMonths,
                                                                 Model model) {
        model.addAttribute("employee request by id for paystub for previous months : "+
                                     employeeId+", "+previousMonths);
        return "my-page";
    }
}
```

We can customize date format using Spring annotation @DateTimeFormat. Following will be mapped with /employees/234/paystubs?startDate=2000-10-31&endDate=2000-10-31"

```java
@Controller
@RequestMapping("employees")
public class EmployeeController{

    @RequestMapping("{id}/paystubs")
    public String handleRequest4 (@PathVariable("id") String employeeId,
              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
              @RequestParam("startDate") LocalDate startDate,
              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
              @RequestParam("endDate") LocalDate endDate,
              Model model) {
        LOGGER.info("employee request by id and paystub dates : " +
                              employeeId + ", " + startDate + ", " + endDate);
        model.addAttribute("employee request by id and paystub dates : " +
                                     employeeId + ", " + startDate + ", " + endDate);
        return "my-page";
    }
}
```

## The 'required' Element of @RequestParam

This element defines whether the parameter is required. The default is true. That means the status code 400 will be returned if the parameter is missing in the request. We can switch this to false if we prefer a null value if the parameter is not present in the request.

Following will be mapped with both /employees/234/report?project=mara and /employees/234/report

```java
@Controller
@RequestMapping("employees")
public class EmployeeController {

    @RequestMapping(value = "{id}/report")
    public String handleEmployeeReportRequest (
              @PathVariable("id") String id,
              @RequestParam(value = "project", required = false) String projectName,
              Model model) {

        model.addAttribute("employee report request by id and project name : " +
                                                             id + ", " + projectName);
        return "my-page";
    }
}
```

## The 'defaultValue' Element of @RequestParam

This element is used as a fallback when the request parameter is not provided or has an empty value. Supplying a default value implicitly sets 'required' to false.

In our last example, we can specify our projectName with a default value. In that case we don't have to specify 'required=false'. We can still make the same two requests. In case of /employees/234/report, the projectName value received by the handler method will be the defaultValue of 'kara'.

```java
@RequestParam(value = "project", defaultValue="kara") String projectName
```

## Same Base URI Methods with Different params are Ambiguous

Defining different request parameters does not define different URI paths. Following handler methods will cause runtime exception, complaining about ambiguous mapping.

```java
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @RequestMapping
    public String handleEmployeeRequestByDept (@RequestParam("dept") String deptName,
                                                                      Model map) {
        map.addAttribute("msg", "employee " + deptName);
        return "my-page";
    }

    @RequestMapping
    public String handleEmployeeRequestByState (@RequestParam("state") String stateCode,
                                            Model map) {
        map.addAttribute("msg", "employee request by  state code : " + ", " + stateCode);
        return "my-page";
    }
} 
```

output:

```shell
Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'myMvcController' method
public java.lang.String com.logicbig.example.EmployeeController.handleEmployeeRequestByArea(java.lang.String,org.springframework.ui.Model)
to {[/employees]}: There is already 'myMvcController' bean method
public java.lang.String com.logicbig.example.EmployeeController.handleEmployeeRequestByDept(java.lang.String,org.springframework.ui.Model) mapped.
```

## Avoiding Ambiguity by Using @RequestMapping(params = ....)

If having a different path is not possible (from the design perceptive) and we don't have other factors with which we can resolve the ambiguity, for example factors like different HTTP methods or consume/produce type, then one way to fix this is to define different params of @RequestMapping . The good news is Spring allows to just specify parameter name without it's value, for example: @RequestMapping(params = "dept"):

```java
@Controller
@RequestMapping("employees")
public class EmployeeController {

   @RequestMapping(params = "dept")
    public String handleEmployeeRequestByDept (@RequestParam("dept") String deptName,
                                                                         Model map) {
        map.addAttribute("msg", "employee request by dept: " + deptName);
        return "my-page";
    }

    @RequestMapping(params = "state")
    public String handleEmployeesRequestByArea (@RequestParam String state, Model map) {
        map.addAttribute("msg", "Employee request by area: " + state);
        return "my-page";
}
```

Alternatively, we can merge different methods into one, having all parameters together with 'required=false'. Or we can use a single Map annotated with @RequestParam with a single handler method. Then based on : what param values are null and what are not, we can make decisions, this will not very ideal approach though.

## Project

http://localhost:8080/spring-mvc-query-param/employees?dept=IT
http://localhost:8080/spring-mvc-query-param/employees?state=NC
http://localhost:8080/spring-mvc-query-param/employees?dept=IT&state=NC
http://localhost:8080/spring-mvc-query-param/employees?dept=IT&state=NC
http://localhost:8080/spring-mvc-query-param/employees/234/paystubs?months=5
http://localhost:8080/spring-mvc-query-param/employees/234/paystubs?startDate=2000-10-31&endDate=2000-10-31
http://localhost:8080/spring-mvc-query-param/employees/234/report
http://localhost:8080/spring-mvc-query-param/employees/234/report
http://localhost:8080/spring-mvc-query-param/employees/234/report?project=mara
http://localhost:8080/spring-mvc-query-param/employees/234/messages?sendBy=mgr&date=20160210