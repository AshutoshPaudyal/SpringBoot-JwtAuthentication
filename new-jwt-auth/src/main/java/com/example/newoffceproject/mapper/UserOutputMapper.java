package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.dto.UserOutputDto;
import com.example.newoffceproject.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserOutputMapper {
    User dtoToUser(UserOutputDto userOutputDto);

    UserOutputDto userToDto(User user);

}
