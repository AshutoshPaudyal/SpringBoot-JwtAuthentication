package com.example.newoffceproject.controller;

import com.example.newoffceproject.exception.InvalidPasswordException;
import com.example.newoffceproject.payload.JwtAuthRequest;
import com.example.newoffceproject.payload.JwtAuthResponse;
import com.example.newoffceproject.security.CustomUserDetailsService;
import com.example.newoffceproject.security.JwtTokenHelper;
import com.example.newoffceproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenHelper jwtTokenHelper;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthResponse jwtAuthResponse;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){

        authenticate(jwtAuthRequest.getEmail(),jwtAuthRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
        String accessToken = jwtTokenHelper.generateToken(userDetails);
        String refreshToken = jwtTokenHelper.refreshToken(userDetails);
        jwtAuthResponse.setAccessToken(accessToken);
        jwtAuthResponse.setRefreshToken(refreshToken);
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

    @GetMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response){

        final String authHeader =request.getHeader(AUTHORIZATION); //first we need the Authorization header
        final String jwt;  //jwt token
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){ //authHeader should always start with Bearer
            return null;
        }
        jwt = authHeader.substring(7);    //extract jwt token from Authorization Header
            userEmail = jwtTokenHelper.extractUsername(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            String accessToken = jwtTokenHelper.generateToken(userDetails);
            String refreshToken = jwtTokenHelper.refreshToken(userDetails);

            jwtAuthResponse.setAccessToken(accessToken);
            jwtAuthResponse.setRefreshToken(refreshToken);
            return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }


}
