package com.example.newoffceproject.mapper;

import com.example.newoffceproject.dto.UserInfoDto;
import com.example.newoffceproject.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-19T12:57:52+0545",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class UserInfoMapperImpl implements UserInfoMapper {

    @Override
    public User dtoToUser(UserInfoDto userInfoDto) {
        if ( userInfoDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userInfoDto.getId() );
        user.name( userInfoDto.getName() );
        user.email( userInfoDto.getEmail() );
        user.phoneNumber( userInfoDto.getPhoneNumber() );

        return user.build();
    }

    @Override
    public UserInfoDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserInfoDto.UserInfoDtoBuilder userInfoDto = UserInfoDto.builder();

        userInfoDto.id( user.getId() );
        userInfoDto.name( user.getName() );
        userInfoDto.email( user.getEmail() );
        userInfoDto.phoneNumber( user.getPhoneNumber() );

        return userInfoDto.build();
    }
}
