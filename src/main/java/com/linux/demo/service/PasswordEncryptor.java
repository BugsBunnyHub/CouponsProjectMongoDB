package com.linux.demo.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class PasswordEncryptor {

    public String encrypt(String password) throws Exception{
        // encrypt the password to 256 bytes
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        // make the byte array into UTF_8
        byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return passToHexString(bytes);
    }

    //stackOverFlow magic
    private String passToHexString(byte[] bytes) {
        BigInteger number = new BigInteger(1, bytes);
        StringBuilder stringBuilder = new StringBuilder(number.toString(16));

        while(stringBuilder.length() < 32) {
            stringBuilder.insert(0, '0');
        }

        return stringBuilder.toString();
    }

}
