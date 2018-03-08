package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController1 {

    @RequestMapping("/path1")
    public String handle(Model model) {
        model.addAttribute("msg", "from MyController1111");
        return "testView1";
    }
}