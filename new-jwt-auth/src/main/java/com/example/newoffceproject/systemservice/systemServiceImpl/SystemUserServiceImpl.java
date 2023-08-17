package com.example.newoffceproject.systemservice.systemServiceImpl;


import com.example.newoffceproject.model.User;
import com.example.newoffceproject.service.UserService;
import com.example.newoffceproject.service.UserServiceForSytemUser;
import com.example.newoffceproject.systemservice.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final UserServiceForSytemUser userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    @Override
    public User getSystemUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByEmail(authentication.getName());

    }
}
