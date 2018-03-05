package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class MyController {

    @RequestMapping("test")
    public RedirectView handleTestRequest (Model model) {
        model.addAttribute("myModelAttribute", "attributeVal");
        RedirectView rv = new RedirectView();
        rv.setUrl("test2");
        return rv;
    }

    @RequestMapping("test2")
    public String handleRequest (@RequestParam Map<String, String> params,
                                 Model model) {
        model.addAttribute("params", params);
        return "my-page";
    }
}