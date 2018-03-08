package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/test")
    public String handle(Model model) {
        model.addAttribute("msg", "test3");
        return "testView";
    }
}