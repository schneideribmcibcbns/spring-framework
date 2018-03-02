package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class EmployeeController {
    
    @RequestMapping("/employee")
    @ResponseBody
    public String getEmployeeByDept (@RequestParam("dept") String deptName) {
        return "test response for dept: " + deptName;
    }
    
    @RequestMapping("/employee2")
    @ResponseBody
    public String getEmployeeByDept2 (@RequestParam(value = "dept", required = false)
                                                String deptName) {
        return "test response for dept: " + deptName;
    }
    
    @RequestMapping("/employee3")
    @ResponseBody
    public String getEmployeeByDept3 (@RequestParam("dept") Optional<String> deptName) {
        return "test response for dept: " + (deptName.isPresent() ? deptName.get() :
                  "using default dept");
    }
}