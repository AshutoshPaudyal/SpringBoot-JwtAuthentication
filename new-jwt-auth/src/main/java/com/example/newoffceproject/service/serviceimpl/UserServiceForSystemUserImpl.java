package com.example.newoffceproject.service.serviceimpl;

import com.example.newoffceproject.model.User;
import com.example.newoffceproject.repo.UserRepo;
import com.example.newoffceproject.service.UserServiceForSytemUser;
import com.example.newoffceproject.systemservice.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceForSystemUserImpl implements UserServiceForSytemUser {

    private final UserRepo userRepo;

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).get();
    }
}
