package com.logicbig.example;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("visitor")
@RequestMapping
public class TradeController {

    @RequestMapping("/trades/**")
    public String handleRequestById (@ModelAttribute("visitor") Visitor visitor, Model model,
                                     HttpServletRequest request, SessionStatus sessionStatus) {

        model.addAttribute("msg", "trades request, serving page " + request.getRequestURI());
        if (request.getRequestURI()
                   .endsWith("clear")) {
            sessionStatus.setComplete();
            return "clear-page";
        } else {
            visitor.addPageVisited(request.getRequestURI());
            return "traders-page";
        }
    }


    
    @ModelAttribute("visitor")
    public Visitor getVisitor (HttpServletRequest request) {
        return new Visitor(request.getRemoteAddr());
    }
}