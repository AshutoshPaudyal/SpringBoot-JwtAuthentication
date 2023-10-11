package com.example.newoffceproject.service.serviceimpl;

import com.example.newoffceproject.dto.UserInfoDto;
import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.dto.UserOutputDto;
import com.example.newoffceproject.exception.*;
import com.example.newoffceproject.mapper.UserInfoMapper;
import com.example.newoffceproject.mapper.UserInputMapper;
import com.example.newoffceproject.mapper.UserOutputMapper;
import com.example.newoffceproject.model.User;
import com.example.newoffceproject.repo.UserRepo;
import com.example.newoffceproject.service.OtpService;
import com.example.newoffceproject.service.UserService;
import com.example.newoffceproject.systemservice.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserInputMapper userInputMapper;
    private final UserOutputMapper userOutputMapper;
    private final OtpService otpService;
    private final SystemUserService systemUserService;
    private final UserInfoMapper userInfoMapper;

    @Override
    public String signUpUser(UserInputDto userInputDto) {
        User user = userInputMapper.dtoToUser(userInputDto);
        Optional<User> byEmail = userRepo.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            throw new SameResourceFound("An account with this email already exists");
        }
        String usersPhoneNumber = userRepo.findUsersPhoneNumber(userInputDto.getPhoneNumber());
        if(usersPhoneNumber!=null){
            throw new GeneralException("This phone number is already in use");
        }
        String password = userInputDto.getConfirmPassword();
        if(!userInputDto.getEnterPassword().equals(password)) {
             throw new ConfirmPasswordException("Entered Password and Confirmation Password Do not Match");
        }

        if(!user.getPhoneNumber().substring(0,4).equalsIgnoreCase("+977")){
            throw new InvalidPhoneNumberCountryCodeException("Phone Number Country Code Not Matched");
        }
        user.setPhoneNumber(userInputDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        return "Account Created";
    }

    @Override
    public String validateEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new GeneralException("An account with this email does not exist"));
        userInputMapper.userToDto(user);
        return "You have parsed valid email :"+email;
    }

    @Override
    public User validatePhoneNumber(String phoneNumber) {
        String cleanedPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        return userRepo.findByPhoneNumber("+"+cleanedPhoneNumber)
                .orElseThrow(() -> new GeneralException("Invalid Phone Number"));
    }

    @Override
    public String sendOtpToUser(String phoneNumber) {
        User user = validatePhoneNumber(phoneNumber);
        Random random = new Random();
        String togenerateOtp = "";
        for (int i = 0; i < 4; i++) {
            int randomNumber = random.nextInt(10);
            togenerateOtp += String.valueOf(randomNumber);
        }
        String toSendOtp = togenerateOtp;
        otpService.generateOtpThroughAkashSms(toSendOtp, user);
        user.setOtp(toSendOtp);
        userRepo.save(user);
        return "Verification OtP has been sent";
    }
    
    @Override
    public String changePassword(String otp, String newPassword, String confirmPassword) {
        Integer userIdByOtp = userRepo.findUserIdByOtp(otp);
        if(userIdByOtp == null){
            throw new GeneralException("Invalid OTP");
        }
        User user = userRepo.findById(userIdByOtp).get();
        if(!newPassword.equals(confirmPassword)){
            throw new GeneralException("New Password and Confirm Password did not match");
        }
        user.setPassword(passwordEncoder.encode(confirmPassword));
        user.setOtp(null);
        userRepo.save(user);
        return "Password Successfully Changed";
    }

    @Override
    public List<UserOutputDto> getAllSignedUpUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(userOutputMapper::userToDto).collect(Collectors.toList());
    }

    @Override
    public UserInfoDto getLoginUserInfo() {
        int loginUserId = systemUserService.getSystemUser().getId();

        User user = userRepo.findById(loginUserId).get();
        return  userInfoMapper.userToDto(user);
    }

}
