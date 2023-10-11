package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.model.User;
import org.mapstruct.Mapper;
import org.springframework.jmx.export.annotation.ManagedAttribute;

@Mapper(componentModel = "spring")
public interface UserInputMapper {

    User dtoToUser(UserInputDto userInputDto);

    UserInputDto userToDto(User user);
}
