package com.logicbig.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.logging.Logger;


@Controller
public class MyController {
    Logger logger = Logger.getLogger(MyController.class.getSimpleName());

    @RequestMapping(value = "test", method = {RequestMethod.GET})
    public HttpEntity<String> handleTestRequest () {

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("test-header", Arrays.asList("test-header-value"));

        HttpEntity<String> responseEntity = new HttpEntity<>("test body", headers);


        logger.info("handler finished");
        return responseEntity;
    }
}