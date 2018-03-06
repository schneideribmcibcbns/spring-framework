package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class I18nExampleController {

    @RequestMapping("/test1")
    @ResponseBody
    public String handleRequest (Locale locale) {
        return String.format("Request received. Language: %s, Country: %s %n",
                             locale.getLanguage(), locale.getDisplayCountry());

    }
}