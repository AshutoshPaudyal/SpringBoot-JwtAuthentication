package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserOutputDto;
import com.example.newoffceproject.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-19T12:57:52+0545",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class UserOutputMapperImpl implements UserOutputMapper {

    @Override
    public User dtoToUser(UserOutputDto userOutputDto) {
        if ( userOutputDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userOutputDto.getId() );
        user.name( userOutputDto.getName() );
        user.email( userOutputDto.getEmail() );
        user.phoneNumber( userOutputDto.getPhoneNumber() );
        user.password( userOutputDto.getPassword() );

        return user.build();
    }

    @Override
    public UserOutputDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserOutputDto.UserOutputDtoBuilder userOutputDto = UserOutputDto.builder();

        userOutputDto.id( user.getId() );
        userOutputDto.name( user.getName() );
        userOutputDto.email( user.getEmail() );
        userOutputDto.phoneNumber( user.getPhoneNumber() );
        userOutputDto.password( user.getPassword() );

        return userOutputDto.build();
    }
}
