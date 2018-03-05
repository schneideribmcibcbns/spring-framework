package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class MyController {

    @RequestMapping
    public HttpEntity<String> handleRequest (HttpEntity<String> requestEntity) {
        System.out.println("request body: " + requestEntity.getBody());
        HttpHeaders headers = requestEntity.getHeaders();
        System.out.println("request headers " + headers);
        HttpEntity<String> responseEntity = new HttpEntity<String>("my response body");
        return responseEntity;
    }

    @RequestMapping("/user")
    public HttpEntity<String> handleUserRequest (HttpEntity<User> requestEntity) {
        User user = requestEntity.getBody();
        System.out.println("request body: " + user);
        System.out.println("request headers " + requestEntity.getHeaders());

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Cache-Control", Arrays.asList("max-age=3600"));

        HttpEntity<String> responseEntity = new HttpEntity<>("my response body", headers);
        return responseEntity;
    }
}