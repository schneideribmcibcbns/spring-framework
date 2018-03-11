package com.logicbig.example;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyController {

    @RequestMapping("/**")
    public String handleRequest2(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext()
                                                   .getAuthentication();
        model.addAttribute("uri", request.getRequestURI())
             .addAttribute("user", auth.getName())
             .addAttribute("roles", auth.getAuthorities());
        return "my-page";
    }
}