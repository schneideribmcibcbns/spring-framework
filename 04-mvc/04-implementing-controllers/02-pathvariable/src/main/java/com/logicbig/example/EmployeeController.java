package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This will cause exception :
 * java.lang.IllegalStateException: Ambiguous handler methods ..
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {


    @RequestMapping("{id}")
    public String handleRequest(@PathVariable("id") String userId, Model model){
        model.addAttribute("msg", "employee id: "+userId);
        return "my-page";

    }

   @RequestMapping("{employeeName}")
    public String handleRequest2 (@PathVariable("employeeName") String userName, Model model) {
        model.addAttribute("msg", "employee name : " + userName);
        return "my-page";
    }
}