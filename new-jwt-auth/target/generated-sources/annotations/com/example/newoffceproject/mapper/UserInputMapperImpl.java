package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserInputDto;
import com.example.newoffceproject.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-19T12:57:52+0545",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class UserInputMapperImpl implements UserInputMapper {

    @Override
    public User dtoToUser(UserInputDto userInputDto) {
        if ( userInputDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userInputDto.getId() );
        user.name( userInputDto.getName() );
        user.email( userInputDto.getEmail() );
        user.phoneNumber( userInputDto.getPhoneNumber() );

        return user.build();
    }

    @Override
    public UserInputDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserInputDto.UserInputDtoBuilder userInputDto = UserInputDto.builder();

        userInputDto.id( user.getId() );
        userInputDto.name( user.getName() );
        userInputDto.email( user.getEmail() );
        userInputDto.phoneNumber( user.getPhoneNumber() );

        return userInputDto.build();
    }
}
