package com.logicbig.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String handleGetRequest (Model model) {
        model.addAttribute("user", new User());
        return "user-registration";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String handlePostRequest (@Valid @ModelAttribute("user") User user,
                                     BindingResult bindingResult,
                                     RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return "user-registration";
        }

        userService.saveUser(user);
        ra.addFlashAttribute("user", user);
        return "redirect:/registration-success";
    }

    @RequestMapping(value = "registration-success", method = RequestMethod.GET)
    public String handleRegistrationDone(@ModelAttribute("user") User user){
        System.out.println("user....: "+user);
        return "registration-done";
    }
}