package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register",
                        method = RequestMethod.POST,
                        consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void handleXMLPostRequest (@RequestBody User user) {
        System.out.println("saving user: " + user);
        userService.saveUser(user);
    }

    @RequestMapping(method = RequestMethod.GET,
                        produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserList handleAllUserRequest () {
        UserList list = new UserList();
        list.setUsers(userService.getAllUsers());
        return list;
    }
}