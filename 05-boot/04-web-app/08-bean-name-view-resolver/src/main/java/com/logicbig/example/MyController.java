package com.logicbig.example;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public String handle(Model model) {
        model.addAttribute("msg", "test msg from controller");
        return "myCustomView";
    }
}