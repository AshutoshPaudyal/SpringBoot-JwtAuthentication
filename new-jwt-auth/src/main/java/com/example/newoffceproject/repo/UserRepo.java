package com.example.newoffceproject.repo;

import com.example.newoffceproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByOtp(String otp);

    @Query(nativeQuery = true,value = "select u.id from users u where u.otp=?1")
    Integer findUserIdByOtp(String otp);

    @Query(nativeQuery = true,value = "select u.phone_number from users u where u.phone_number=?1")
    String findUsersPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
