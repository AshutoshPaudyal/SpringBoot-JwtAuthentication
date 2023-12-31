package com.example.newoffceproject.security;

import com.example.newoffceproject.exception.GeneralException;
import io.jsonwebtoken.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtTokenHelper {

    private static final String SECRET_KEY = "72357538782F4125442A472D4B6150645367566B597033733676397924422645";
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    //to extract Single Token
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        if(extractAllClaims(token)==null){
            throw new GeneralException("lol");
        }
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(
            UserDetails userDetails
    ){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaim,
            UserDetails userDetails
    ){
        return Jwts.
                builder()
                .setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String refreshToken(
            UserDetails userDetails
    ){
        return refreshToken(new HashMap<>(),userDetails);
    }
    public String refreshToken(
            Map<String, Object> extraClaim,
            UserDetails userDetails
    ){
        return Jwts.
                builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 172800000)) //1000 millisecond + 24 hrs
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    // Claims are used to store information about user like full name, phone number, email address
    public Claims extractAllClaims(String token){
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
