package com.example.newoffceproject.systemservice;


import com.example.newoffceproject.model.User;
import org.springframework.security.core.Authentication;

public interface SystemUserService {
    Authentication getAuthentication();
    User getSystemUser();
}
