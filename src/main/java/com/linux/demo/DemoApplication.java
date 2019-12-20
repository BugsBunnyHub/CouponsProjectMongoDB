package com.linux.demo;

import com.linux.demo.beans.Role;
import com.linux.demo.beans.User;
import com.linux.demo.mongo.dao.UserDAO;
import com.linux.demo.service.PasswordEncryptor;
import com.linux.demo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    // logger for info INFO: process ID: print: as in spring boot start
    private Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDAO userDAO;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String ...args) throws Exception {

        User user = new User(null, "daniel", "d123", Role.ADMIN);
        userDAO.insertUser(user);

    }
}
