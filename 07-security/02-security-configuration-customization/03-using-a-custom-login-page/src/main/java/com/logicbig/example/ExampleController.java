package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class ExampleController {

    @RequestMapping("/")
    public String handleRequest2(ModelMap map) {
        map.addAttribute("time", LocalDateTime.now().toString());
        return "my-page";
    }
}