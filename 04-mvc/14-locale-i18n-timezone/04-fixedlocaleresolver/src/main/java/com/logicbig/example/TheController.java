package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class TheController {

    @RequestMapping("/")
    @ResponseBody
    public String handleRequest (Locale locale) {
        return String.format("Request received. Locale: %s%n",
                             locale);
    }
}