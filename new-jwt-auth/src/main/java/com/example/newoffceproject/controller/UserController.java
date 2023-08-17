package com.example.newoffceproject.controller;


import com.example.newoffceproject.model.User;
import com.example.newoffceproject.service.UserServiceForSytemUser;
import com.example.newoffceproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("createUser")
    private ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("getAllUsers")
    private ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @GetMapping("getUserById")
    private ResponseEntity<User> getUserById(){
        User byId = userService.findById();
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }

}
