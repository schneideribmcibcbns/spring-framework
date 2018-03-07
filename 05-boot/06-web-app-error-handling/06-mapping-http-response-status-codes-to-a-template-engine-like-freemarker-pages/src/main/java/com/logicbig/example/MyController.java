package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/")
    public void handleRequest() {
        throw new RuntimeException("test exception");
    }

    @RequestMapping("/app-info")
    public void handleAppInfoRequest() throws NotYetImplemented {
        throw new NotYetImplemented("The request page is not yet implemented");
    }

    @RequestMapping("/admin")
    public void handleAdminRequest() throws ForbiddenException {
        throw new ForbiddenException("The requested page is forbidden");
    }
}