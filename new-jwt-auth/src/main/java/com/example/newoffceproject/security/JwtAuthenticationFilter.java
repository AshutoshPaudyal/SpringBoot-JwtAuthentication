package com.example.newoffceproject.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //first we need the Authorization header
        final String jwt;  //jwt token
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) { //authHeader should always start with Bearer
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);    //extract jwt token from Authorization Header

        // todo extract userEmail from JWT token
        try {
            userEmail = jwtTokenHelper.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtTokenHelper.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException exception) {
            log.info("Token expired error: {}", exception.getMessage());
            Map<String, String> error = new HashMap<>();
            response.setStatus(403);
            error.put("error", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(), error);
        } catch (SignatureException exception) {
            log.info("Token validation error: {}", exception.getMessage());
            Map<String, String> error = new HashMap<>();
            response.setStatus(401);
            error.put("error", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(), error);
        }
    }
}