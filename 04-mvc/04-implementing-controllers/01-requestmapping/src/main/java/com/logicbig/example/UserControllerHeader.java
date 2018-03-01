package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserControllerHeader {

    @RequestMapping(headers = "id=4")
    public String handleAllUsersRequest() {
        System.out.println("got header id = 4");
        return "";
    }

    @RequestMapping(headers = "id=10")
    public String handleAllUsersRequest2() {
        System.out.println("got header id = 10");
        return "";
    }
}