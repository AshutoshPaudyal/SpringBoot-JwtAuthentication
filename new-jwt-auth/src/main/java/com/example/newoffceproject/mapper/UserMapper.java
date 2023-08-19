package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserDto;
import com.example.newoffceproject.model.User;
import org.springframework.stereotype.Component;


public interface UserMapper {

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}
