package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController2 {

    @RequestMapping("/path2")
    public String handle(Model model) {
        model.addAttribute("msg", "from MyController2");
        return "testView2";
    }
}