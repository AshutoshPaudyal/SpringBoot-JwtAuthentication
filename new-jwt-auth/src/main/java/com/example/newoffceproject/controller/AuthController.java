package com.example.newoffceproject.controller;

import com.example.newoffceproject.exception.InvalidPasswordException;
import com.example.newoffceproject.payload.JwtAuthRequest;
import com.example.newoffceproject.payload.JwtAuthResponse;
import com.example.newoffceproject.security.CustomUserDetailsService;
import com.example.newoffceproject.security.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenHelper jwtTokenHelper;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthResponse jwtAuthResponse;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){

        authenticate(jwtAuthRequest.getEmail(),jwtAuthRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);
        jwtAuthResponse.setToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            authenticationManager.authenticate(authenticationToken);

        }catch (BadCredentialsException e){
            throw new InvalidPasswordException("Invalid Password");
        }
    }
}
