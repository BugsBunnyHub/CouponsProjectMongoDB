package com.linux.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.linux.demo.beans.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    // creates a token with the secret located in application.yml
    public String createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                // token is valid for 1 hour
                .withExpiresAt(new Date(new Date().toInstant().plusSeconds(60 * 60).toEpochMilli()))
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().name())
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .acceptLeeway(10)
                .acceptExpiresAt(10)
                .build();
        return verifier.verify(token);
    }

}
