package com.logicbig.example.jspjar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/")
    public String handler (Model model) {
        model.addAttribute("msg",
                           "a jar packaging example");
        return "myView";
    }
}