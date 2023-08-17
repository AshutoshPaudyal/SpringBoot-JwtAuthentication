package com.example.newoffceproject.service.serviceimpl;

import com.example.newoffceproject.model.User;
import com.example.newoffceproject.repo.UserRepo;
import com.example.newoffceproject.service.UserService;
import com.example.newoffceproject.systemservice.SystemUserService;
import com.example.newoffceproject.systemservice.systemServiceImpl.SystemUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SystemUserService systemUserService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public User createUser(User user) {
        Optional<User> byEmail = userRepo.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            throw new RuntimeException("User with same email found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findById() {
        Integer loginUserId = systemUserService.getSystemUser().getId();
        return userRepo.findById(loginUserId).get();
    }



}
