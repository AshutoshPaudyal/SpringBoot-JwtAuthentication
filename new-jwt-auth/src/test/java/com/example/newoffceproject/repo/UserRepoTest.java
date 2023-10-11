package com.example.newoffceproject.repo;

import com.example.newoffceproject.model.User;
import jakarta.validation.valueextraction.UnwrapByDefault;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    void canFindUserByEmail() {
        String email = "haha1@gmail.com";
        //given
        User user = User.builder()
                .email(email)
                .phoneNumber("9800000001")
                .build();
        userRepo.save(user);
        //when
        User userByEmail = userRepo.findByEmail(email).get();
        //thepn
        assertThat(userByEmail.getEmail()).isEqualTo(user.getEmail());

        assertThat(userByEmail.getPhoneNumber()).isEqualTo(user.getPhoneNumber());

    }
    @Test
    void canFindUserByOtp() {
        String email = "haha2@gmail.com";
        String otp= "5678";
        //given
        User user = User.builder()
                .email(email)
                .phoneNumber("9800000002")
                .otp(otp)
                .build();
        userRepo.save(user);
        //when
        User userByOtp = userRepo.findByOtp(otp).get();
        //then
        assertThat(userByOtp.getOtp()).isEqualTo(user.getOtp());

        assertThat(userByOtp.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(userByOtp.getId()).isEqualTo(user.getId());
    }

    @Test
    void canFindUserIdByOtp() {
        String email = "haha3@gmail.com";
        String otp= "8888";
        //given
        User user = User.builder()
                .email(email)
                .phoneNumber("9800000003")
                .otp(otp)
                .build();
        userRepo.save(user);
        //when
        Integer userIdByOtp = userRepo.findUserIdByOtp(otp);
        User userFindById = userRepo.findById(userIdByOtp).get();
        //then
        assertThat(userIdByOtp).isEqualTo(user.getId());
        assertThat(userFindById.getEmail()).isEqualTo(user.getEmail());
        assertThat(user).isEqualTo(userFindById);
    }

    @Test
    void canFindUsersPhoneNumber() {

        String email = "haha4@gmail.com";
        String otp= "9999";
        //given
        User user = User.builder()
                .email(email)
                .phoneNumber("9800000004")
                .otp(otp)
                .build();
        userRepo.save(user);
        //when
        String userPhoneNumber = userRepo.findUsersPhoneNumber(user.getPhoneNumber());
        User userByPhoneNumber = userRepo.findByPhoneNumber(userPhoneNumber).get();
        //then
        assertThat(userPhoneNumber).isEqualTo(user.getPhoneNumber());
        assertThat(userByPhoneNumber.getEmail()).isEqualTo(user.getEmail());
        assertThat(userByPhoneNumber).isEqualTo(user);
    }

    @Test
    void canFindByPhoneNumber() {
        String email = "haha5@gmail.com";
        String otp= "7777";
        //given
        User user = User.builder()
                .email(email)
                .phoneNumber("9800000005")
                .otp(otp)
                .build();
        userRepo.save(user);
        //when
        User userByPhoneNumber = userRepo.findByPhoneNumber(user.getPhoneNumber()).get();
        //then
        assertThat(userByPhoneNumber.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(user.getId()).isEqualTo(userByPhoneNumber.getId());
        assertThat(user.getEmail()).isEqualTo(userByPhoneNumber.getEmail());
        assertThat(userByPhoneNumber).isEqualTo(user);
    }
}