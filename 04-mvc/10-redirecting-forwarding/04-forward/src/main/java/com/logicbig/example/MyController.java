package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class MyController {

    @RequestMapping(value = "test/{id}")
    public String handleTestRequest (@PathVariable("id") String id, Model model) {
        if (!id.matches("\\d+")) {
            model.addAttribute("msg", "id should only have digits");
            return "error-page";
        } else {
            return "forward:/testFinal/" + id + "?attr=attrVal";
        }
    }

    @RequestMapping("testFinal/{id}")
    public String handleRequestFinal (@PathVariable("id") String id,
                                      @RequestParam("attr") String attr,
                                      Model model) {

        model.addAttribute("id", id);
        model.addAttribute("attr", attr);
        return "my-page";
    }

    @RequestMapping(value = "test2/{id}")
    public String handleTestRequest2 (@PathVariable("id") String id,
                                      Model model, HttpServletRequest request) {
        if (!id.matches("\\d+")) {
            model.addAttribute("msg", "id should only have digits");
            return "error-page";
        } else {

            request.setAttribute("attr", "attrVal");
            return "forward:/testFinal2/" + id;
        }
    }

    @RequestMapping("testFinal2/{id}")
    public String handleRequestFinal2 (@PathVariable("id") String id,
                                       HttpServletRequest request,
                                       Model model) {

        model.addAttribute("id", id);
        model.addAttribute("attr", request.getAttribute("attr"));
        return "my-page";
    }
}