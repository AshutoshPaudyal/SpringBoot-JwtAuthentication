package com.example.newoffceproject.controller;


import com.example.newoffceproject.dto.UserDto;
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
    private ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
