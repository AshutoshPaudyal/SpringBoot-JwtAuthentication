package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserInfoDto;
import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoMapper{
    User dtoToUser(UserInfoDto userInfoDto);
    UserInfoDto userToDto(User user);
}
