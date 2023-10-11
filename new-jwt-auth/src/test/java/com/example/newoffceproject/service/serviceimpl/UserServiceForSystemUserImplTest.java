package com.example.newoffceproject.service.serviceimpl;

import com.example.newoffceproject.model.User;
import com.example.newoffceproject.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserServiceForSystemUserImplTest {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserServiceForSystemUserImpl userServiceForSystemUser;

    @Test
    void canGetUserByEmail() {
        String email = "haha@gmail.com";
        User user = User.builder()
                        .email(email)
                        .otp("6789")
                        .phoneNumber("+97765489765")
                                .build();
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User userByEmail = userServiceForSystemUser.getUserByEmail(email);

        assertThat(userByEmail.getEmail()).isEqualTo(user.getEmail());
        assertThat(userByEmail.getOtp()).isEqualTo(user.getOtp());
        assertThat(userByEmail.getPhoneNumber()).isEqualTo(userByEmail.getPhoneNumber());
    }
}