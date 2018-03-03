package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExampleController {

    @RequestMapping("/")
    @ResponseBody
    public String handle (@RequestAttribute("visitorCounter") Integer counter) {
        return String.format("Visitor number: %d", counter);
    }
}