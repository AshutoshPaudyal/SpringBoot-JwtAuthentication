package com.example.newoffceproject.service.serviceimpl;

import com.example.newoffceproject.dto.UserDto;
import com.example.newoffceproject.exception.ConfirmPasswordException;
import com.example.newoffceproject.exception.InvalidPhoneNumberCountryCodeException;
import com.example.newoffceproject.exception.ResourceNotFound;
import com.example.newoffceproject.exception.SameResourceFound;
import com.example.newoffceproject.mapper.UserMapper;
import com.example.newoffceproject.model.User;
import com.example.newoffceproject.repo.UserRepo;
import com.example.newoffceproject.service.UserService;
import com.example.newoffceproject.systemservice.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SystemUserService systemUserService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.dtoToUser(userDto);
        Optional<User> byEmail = userRepo.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            throw new SameResourceFound( "User with same email found");
        }
        if(!user.getEnterPassword().equals(user.getConfirmPassword())) {
             throw new ConfirmPasswordException("Entered Password and Confirmation Password Do not Match");
        }

        if(!user.getPhoneNumber().substring(0,4).equalsIgnoreCase("+977")){
            throw new InvalidPhoneNumberCountryCodeException("Phone Number Country Code Not Matched");
        }
        user.setEnterPassword(passwordEncoder.encode(user.getEnterPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        User save = userRepo.save(user);
        return userMapper.userToDto(save);
    }



}
