package com.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.entity.Users;
import com.security.security.UserService;

@RestController
@RequestMapping(path = "/")
public class UserController {
   
   private @Autowired UserService userService;

   @PostMapping(path = "register")
   public Users save(@RequestBody Users entity){
      return userService.save(entity);
   }

   @GetMapping(path = "findAll")
   public Iterable<Users> findAllUsers(){
      return userService.findAllUsers();
   }

   @GetMapping(path = "/home")
   public String home(){
      return "<h1>still develop</h1>";
   }
}
