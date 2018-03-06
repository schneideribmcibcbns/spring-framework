package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class I18nExampleController2 {

    @Autowired
    MessageSource messageSource;

    @RequestMapping("/test2")
    @ResponseBody
    public String handleRequest (Locale locale) {

        return messageSource.getMessage(
                  "app.name", new Object[]{"Joe"}, locale);
    }
}