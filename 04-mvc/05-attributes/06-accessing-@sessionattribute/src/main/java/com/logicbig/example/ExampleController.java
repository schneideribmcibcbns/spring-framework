package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class ExampleController {

    @RequestMapping("/")
    @ResponseBody
    public String handle (@SessionAttribute(name = "sessionStartTime")
                          LocalDateTime startDateTime) {

        Duration d = Duration.between(startDateTime, LocalDateTime.now());
        return String.format("First Visit time: %s<br/> Visiting site for:" +
                                       " %s seconds", startDateTime, d.getSeconds());
    }
}