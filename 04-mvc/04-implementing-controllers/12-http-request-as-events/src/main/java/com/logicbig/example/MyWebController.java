package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyWebController {
    
    @RequestMapping("/test")
    @ResponseBody
    public String handle () {
        return "test response from /test";
    }
    
    @RequestMapping("/test2")
    @ResponseBody
    public String handle2 () {
        return "test response from /test2";
    }
}