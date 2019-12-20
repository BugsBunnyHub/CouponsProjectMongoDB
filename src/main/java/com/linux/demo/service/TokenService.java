package com.linux.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.linux.demo.beans.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    // creates a token with the secret located in application.yml
    public String createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("Just give it a name")
                // token is valid for 1 hour
                .withExpiresAt(new Date(new Date().toInstant().plusSeconds(60*60).toEpochMilli()))
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().name())
                .sign(algorithm);
    }

}
