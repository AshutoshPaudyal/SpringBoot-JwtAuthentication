package com.example.newoffceproject.service.serviceimpl;

import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.dto.UserOutputDto;
import com.example.newoffceproject.exception.*;
import com.example.newoffceproject.mapper.UserInputMapper;
import com.example.newoffceproject.mapper.UserOutputMapper;
import com.example.newoffceproject.model.User;
import com.example.newoffceproject.repo.UserRepo;
import com.example.newoffceproject.service.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserInputMapper userInputMapper;

    @Mock
    private UserOutputMapper userOutputMapper;

    @Mock
    private OtpServiceImpl otpService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User user;
    private UserInputDto userInputDto;
    private String email;
    private String phoneNumber;


    @BeforeEach
    public void setup(){
        phoneNumber = "+9779814389639";
        email = "haha@gmail.com";

        userInputDto = UserInputDto.builder()
                .name("Ashutosh")
                .email(email)
                .enterPassword("haha")
                .confirmPassword("haha")
                .phoneNumber(phoneNumber)
                .build();
        user = User.builder()
                .name("Ashutosh")
                .email(email)
                .password(passwordEncoder.encode(userInputDto.getConfirmPassword()))
                .phoneNumber(userInputDto.getPhoneNumber())
                .otp("9856")
                .build();
    }

    @Test
    void givenUserInputDto_whenSignUpUser_thenReturnString() {
        given(userInputMapper.dtoToUser(userInputDto)).willReturn(user);
        given(userRepo.findByEmail(email)).willReturn(Optional.empty());

        //when
        String signUpUserMessage = userService.signUpUser(userInputDto);

        //then
        ArgumentCaptor<User> studentArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(studentArgumentCaptor.capture());
        User capturedUser = studentArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
        assertThat(capturedUser.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(capturedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(capturedUser.getEmail()).isEqualTo(email);

        assertThat(signUpUserMessage).isEqualTo("Account Created");

    }

    @Test
    void givenExistingEmail_whenSignUpUser_throwsException(){

        given(userInputMapper.dtoToUser(userInputDto)).willReturn(user);
        given(userRepo.findByEmail(anyString())).willReturn(Optional.of(user));

        assertThatThrownBy(()-> userService.signUpUser(userInputDto))
                .isInstanceOf(SameResourceFound.class)
                .hasMessageContaining("An account with this email already exists");

        verify(userRepo,never()).save(any());
    }

    @Test
    void givenExistingPhone_whenSignUpUser_throwsException(){

        given(userInputMapper.dtoToUser(userInputDto)).willReturn(user);
        given(userRepo.findUsersPhoneNumber(anyString())).willReturn(user.getPhoneNumber());

        assertThatThrownBy(()-> userService.signUpUser(userInputDto))
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining("This phone number is already in use");

        verify(userRepo,never()).save(any());

    }

    @Test
    void givenEnteredAndConfirmPassword_whenSignUpUser_throwsException() {

        given(userInputMapper.dtoToUser(userInputDto)).willReturn(user);
        given(userRepo.findByEmail(anyString())).willReturn(Optional.empty());
        given(userRepo.findUsersPhoneNumber(phoneNumber)).willReturn(null);

        userInputDto.setConfirmPassword("pass");
        userInputDto.setEnterPassword("password");

        assertThatThrownBy(()-> userService.signUpUser(userInputDto))
                .isInstanceOf(ConfirmPasswordException.class)
                .hasMessageContaining("Entered Password and Confirmation Password Do not Match");

        verify(userRepo,never()).save(any());
    }

    @Test
    void givenInvalidPhoneNumber_whenSignUpUser_throwsException(){
        given(userInputMapper.dtoToUser(userInputDto)).willReturn(user);
        given(userRepo.findByEmail(anyString())).willReturn(Optional.empty());
        given(userRepo.findUsersPhoneNumber(phoneNumber)).willReturn(null);
        userInputDto.setConfirmPassword("pass");
        userInputDto.setEnterPassword("pass");

        user.setPhoneNumber("9800000000");

        assertThatThrownBy(()-> userService.signUpUser(userInputDto))
                .isInstanceOf(InvalidPhoneNumberCountryCodeException.class)
                .hasMessageContaining("Phone Number Country Code Not Matched");
    }

    @Test
    void givenEmail_validateEmail_returnString() {
        //given
        given(userRepo.findByEmail(email)).willReturn(Optional.of(user));

        //when
        String validateEmailString = userService.validateEmail(email);

        //then
        ArgumentCaptor<User> studentArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userInputMapper).userToDto(studentArgumentCaptor.capture());
        User capturedUser = studentArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(capturedUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(validateEmailString).isEqualTo("You have parsed valid email :"+capturedUser.getEmail());

    }

    @Test
    void givenInvalidEmail_validateEmail_returnString(){

        given(userRepo.findByEmail(email)).willReturn(Optional.empty());

        assertThatThrownBy(()-> userService.validateEmail(email))
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining("An account with this email does not exist");
    }

    @Test
    void givenPhoneNumber_validatePhoneNumber_returnUser(){
        String cleanedPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        given(userRepo.findByPhoneNumber("+"+cleanedPhoneNumber)).willReturn(Optional.of(user));

        User userByPhoneNumber = userService.validatePhoneNumber(phoneNumber);
        assertThat(userByPhoneNumber).isEqualTo(user);
        assertThat(userByPhoneNumber.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(userByPhoneNumber.getEmail()).isEqualTo(user.getEmail());
    }
    @Test
    void givenInvalidPhoneNumber_validatePhoneNumber_returnUser(){

        given(userRepo.findByPhoneNumber(phoneNumber)).willReturn(Optional.empty());

        assertThatThrownBy(()-> userService.validatePhoneNumber(phoneNumber))
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining("Invalid Phone Number");
    }
    @Test
    void givenPhoneNumber_sendOtpToUser_returnString() {
        given(userRepo.findByPhoneNumber(isA(String.class))).willReturn(Optional.of(user));
        doNothing().when(otpService).generateOtpThroughAkashSms(isA(String.class),isA(User.class));
        given(userRepo.save(user)).willReturn(user);
        String otpSent = userService.sendOtpToUser(phoneNumber);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();


        assertThat(capturedUser.getOtp()).isEqualTo(user.getOtp());
        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(otpSent).isEqualTo("Verification OtP has been sent");
    }

    @Test
    void givenOtpNewPasswordConfirmPassword_changePassword_returnString() {
        Integer id =1;
        when(userRepo.findUserIdByOtp(isA(String.class))).thenReturn(id);
        when(userRepo.findById(isA(Integer.class))).thenReturn(Optional.of(user));

        String changePasswordValue = userService.changePassword("8888", "pass", "pass");

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userArgumentCaptor.capture());
        User capturedUser =  userArgumentCaptor.getValue();

        assertThat(changePasswordValue).isEqualTo("Password Successfully Changed");
        assertThat(capturedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(capturedUser.getId()).isEqualTo(capturedUser.getId());
    }

    @Test
    void givenInvalidOtp_changePassword_throwsException(){
        when(userRepo.findUserIdByOtp("")).thenReturn(null);
        assertThatThrownBy(()-> userService.changePassword("","pass","pass"))
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining("Invalid OTP");
    }
    @Test
    void givenNotMatchingNewAndConfirmPassword_changePassword_throwsException(){
        Integer id = 1;
        when(userRepo.findUserIdByOtp("")).thenReturn(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        assertThatThrownBy(()-> userService.changePassword("","passw","pass"))
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining("New Password and Confirm Password did not match");
    }

    @Test
    void getAllSignedUpUsers() {
        User firstUser = User.builder().name("Ashutosh").build();
        User secondUser = User.builder().name("Walter").build();

        List<User> userList = List.of(firstUser, secondUser);

        UserOutputDto firstUserDto  = UserOutputDto.builder().name("haha").build();
        UserOutputDto secondUserDto = UserOutputDto.builder().name("lala").build();

        when(userRepo.findAll()).thenReturn(userList);

        when(userOutputMapper.userToDto(firstUser)).thenReturn(firstUserDto);
        when(userOutputMapper.userToDto(secondUser)).thenReturn(secondUserDto);


        List<UserOutputDto> allSignedUpUsers = userService.getAllSignedUpUsers();

        assertThat(allSignedUpUsers.size()).isEqualTo(2);
        assertThat(allSignedUpUsers.get(0).getName()).isEqualTo("haha");
        assertThat(allSignedUpUsers.get(1).getName()).isEqualTo("lala");
    }
}