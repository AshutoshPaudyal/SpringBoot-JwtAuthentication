package com.example.newoffceproject.service;

import com.example.newoffceproject.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    List<User> getAllUsers();

    User findById();

}
