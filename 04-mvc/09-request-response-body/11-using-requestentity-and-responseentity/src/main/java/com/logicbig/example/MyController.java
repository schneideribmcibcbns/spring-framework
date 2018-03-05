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

    @RequestMapping("test")
    public ResponseEntity<String> handleRequest (RequestEntity<String> requestEntity) {
        System.out.println("request body : " + requestEntity.getBody());
        HttpHeaders headers = requestEntity.getHeaders();
        System.out.println("request headers : " + headers);
        HttpMethod method = requestEntity.getMethod();
        System.out.println("request method : " + method);
        System.out.println("request url: " + requestEntity.getUrl());

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body",
                                                                     HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("/user")
    public ResponseEntity<String> handleUserRequest (RequestEntity<User> requestEntity) {
        User user = requestEntity.getBody();
        System.out.println("request body: " + user);
        System.out.println("request headers " + requestEntity.getHeaders());
        System.out.println("request method : " + requestEntity.getMethod());

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Cache-Control", Arrays.asList("max-age=3600"));

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body",
                                                                     headers,
                                                                     HttpStatus.OK);
        return responseEntity;
    }
}