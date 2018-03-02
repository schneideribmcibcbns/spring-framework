package com.logicbig.example;

import org.springframework.stereotype.Controller;

@Controller
public class TheController {

    @ResourceGone(value = "/link1")
    public String handle1 () {
        return "The resource 'link1' doesn't exist anymore";
    }
    
    @ResourceGone(value = "/link2")
    public String handle2 () {
        return "The resource 'link2' doesn't exist anymore";
    }
}