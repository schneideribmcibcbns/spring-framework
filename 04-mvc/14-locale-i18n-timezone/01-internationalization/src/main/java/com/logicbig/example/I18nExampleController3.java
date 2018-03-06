package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class I18nExampleController3 {

    @RequestMapping("/test3")
    public String handleRequest (Model model) {
        model.addAttribute("by", "Joe");
        return "myPage";
    }
}