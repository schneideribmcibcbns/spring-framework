package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


@Controller
public class MyController {

    @RequestMapping(value = "test/{id}")
    public String handleTestRequest (@PathVariable("id") String id, Model model) {
        if (!id.matches("\\d+")) {
            model.addAttribute("msg", "id should only have digits");
            return "error-page";
        } else {
            model.addAttribute("attr", "attrVal");
            return "redirect:/test2/{id}";
        }
    }

    @RequestMapping("test2/{id}")
    public String handleRequest (@PathVariable("id") String id,
                                 @RequestParam("attr") String attr,
                                 Model model) {

        model.addAttribute("id", id);
        model.addAttribute("attr", attr);
        return "my-page";
    }
}