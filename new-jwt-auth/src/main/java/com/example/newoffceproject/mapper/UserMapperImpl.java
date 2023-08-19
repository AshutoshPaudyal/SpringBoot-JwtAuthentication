package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserDto;
import com.example.newoffceproject.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper{
    private final ModelMapper modelMapper;
    @Override
    public User dtoToUser(UserDto userDto) {
        return modelMapper.map(userDto,User.class);
    }

    @Override
    public UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
