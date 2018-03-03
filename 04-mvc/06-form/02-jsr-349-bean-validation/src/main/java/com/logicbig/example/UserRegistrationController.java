package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest () {
        return "user-registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handlePostRequest (@Valid User user, BindingResult bindingResult,
                                     Model model) {

        if (bindingResult.hasErrors()) {
            populateError("name", model, bindingResult);
            populateError("emailAddress", model, bindingResult);
            populateError("password", model, bindingResult);

            return "user-registration";
        }

        userService.saveUser(user);
        return "registration-done";
    }

    private void populateError (String field, Model model, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            model.addAttribute(field + "Error", bindingResult.getFieldError(field)
                                                             .getDefaultMessage());
        }
    }
}