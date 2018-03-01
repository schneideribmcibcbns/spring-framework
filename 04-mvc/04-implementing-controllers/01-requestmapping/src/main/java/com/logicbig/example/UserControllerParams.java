package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserControllerParams {

    @RequestMapping(params = "id=4")
    public String handleUserId4() {
        System.out.println("got param id = 4");
        return "";
    }

    @RequestMapping(params = "id=10")
    public String handleUserId10() {
        System.out.println("got param id = 10");
        return "";
    }
}