package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/**/users")
public class UserControllerPath {

    @RequestMapping
    public void handleAllUsersRequest(HttpServletRequest request){
        System.out.println(request.getRequestURL());
    }
}