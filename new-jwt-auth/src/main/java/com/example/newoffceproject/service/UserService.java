package com.example.newoffceproject.service;

import com.example.newoffceproject.dto.UserInfoDto;
import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.dto.UserOutputDto;
import com.example.newoffceproject.model.User;

import java.util.List;

public interface UserService {

    String signUpUser(UserInputDto user);

    String validateEmail(String email);

    User validatePhoneNumber(String phoneNumber);

    String sendOtpToUser(String phoneNumber);

    String changePassword(String otp,String newPassword,String confirmPassword);

    List<UserOutputDto> getAllSignedUpUsers();

    UserInfoDto getLoginUserInfo();

}
