package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Using mutually exclusive regex, which can be used
 * to avoid ambiguous mapping exception
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @RequestMapping("{id:[0-9]+}")
    public String handleRequest(@PathVariable("id") String userId, Model model){
        model.addAttribute("msg", "profile id: "+userId);
        return "my-page";

    }

    @RequestMapping("{name:[a-zA-Z]+}")
    public String handleRequest2 (@PathVariable("name") String deptName, Model model) {
        model.addAttribute("msg", "dept name : " + deptName);
        return "my-page";
    }
}