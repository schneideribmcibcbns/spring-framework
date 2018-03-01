package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserControllerHttpMethods {

   @RequestMapping(value= "{id}", method = {RequestMethod.GET})
   public String handleGet(@PathVariable("id") String userId){
     System.out.println("in get method");
      return "";
   }

   @RequestMapping(value= "{id}", method = {RequestMethod.DELETE})
   public String handleDelete(@PathVariable("id") String userId){
      System.out.println("in delete method");
     return "";
   }
}