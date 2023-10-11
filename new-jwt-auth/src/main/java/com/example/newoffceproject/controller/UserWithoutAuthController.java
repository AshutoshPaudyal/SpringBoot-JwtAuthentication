package com.example.newoffceproject.controller;


import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.dto.UserOutputDto;
import com.example.newoffceproject.model.User;
import com.example.newoffceproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersWithoutAuth")
@RequiredArgsConstructor
public class UserWithoutAuthController {

    private final UserService userService;

    @PostMapping("signUpUser")
    private ResponseEntity<String> signUpUser(@RequestBody UserInputDto userInputDto){
        String registerUser = userService.signUpUser(userInputDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }
    @GetMapping("validateEmail")
    private ResponseEntity<String> validateEmail(@RequestParam("email") String email){
        String validEmailParsed = userService.validateEmail(email);
        return new ResponseEntity<>(validEmailParsed,HttpStatus.OK);
    }
    @GetMapping("validatePhoneNumber")
    private ResponseEntity<User> validatePhoneNumber(@RequestParam String phoneNumber){
        User user = userService.validatePhoneNumber(phoneNumber);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping("sendOtpToUser")
    private ResponseEntity<String> sendOtpToUser(@RequestParam("phoneNumber") String phoneNumber){
        String otpSentSuccessFully = userService.sendOtpToUser(phoneNumber);
        return new ResponseEntity<>(otpSentSuccessFully,HttpStatus.OK);
    }
    @PutMapping("changePassword")
    private ResponseEntity<String> changePassword(@RequestParam String otp,
                                                   @RequestParam String newPassword,
                                                   @RequestParam String confirmPassword){
        String passwordChangedSuccessfully = userService.changePassword(otp, newPassword, confirmPassword);
        return new ResponseEntity<>(passwordChangedSuccessfully,HttpStatus.OK);
    }
    @GetMapping("getAllSignedUpUsers")
    private ResponseEntity<List<UserOutputDto>> getAllSignedUpUsers(){
        List<UserOutputDto> allSignUpdUsers = userService.getAllSignedUpUsers();
        return new ResponseEntity<>(allSignUpdUsers,HttpStatus.OK);
    }

}
