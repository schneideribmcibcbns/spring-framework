package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/")
    public String handler (Model model) {
        model.addAttribute("msg",
                           "a war packaging example");
        return "myView";
    }
}