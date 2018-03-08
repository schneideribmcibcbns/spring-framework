package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/test")
    public String handle(Model model) {
        model.addAttribute("msg", "a message from controller");
        return "testView";
    }

    @RequestMapping("/test2")
    public String handle2(Model model) {
        model.addAttribute("msg2", "a message from controller 2");
        return "testView2";
    }
}