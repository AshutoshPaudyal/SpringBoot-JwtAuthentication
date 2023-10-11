package com.example.newoffceproject.controller;

import com.example.newoffceproject.dto.UserInfoDto;
import com.example.newoffceproject.dto.UserOutputDto;
import com.example.newoffceproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usersWithAuth")
@RequiredArgsConstructor
public class UserWithAuthController {
    private final UserService userService;

    @GetMapping("getLoginUserInfo")
    public ResponseEntity<UserInfoDto> getLoginUserInfo(){
        UserInfoDto loginUserInfo = userService.getLoginUserInfo();
        return new ResponseEntity<>(loginUserInfo, HttpStatus.OK);
    }
    @GetMapping("getAllSignedUpUsers")
    private ResponseEntity<List<UserOutputDto>> getAllSignedUpUsers(){
        List<UserOutputDto> allSignUpdUsers = userService.getAllSignedUpUsers();
        return new ResponseEntity<>(allSignUpdUsers,HttpStatus.OK);
    }

}
