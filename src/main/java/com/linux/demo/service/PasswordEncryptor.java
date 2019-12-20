package com.linux.demo.service;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class PasswordEncryptor {

    public String encrypt(String password) throws Exception {
        HashFunction hashFunction = Hashing.sha256();
        return hashFunction.hashString(password, StandardCharsets.UTF_8).toString();
    }

}
