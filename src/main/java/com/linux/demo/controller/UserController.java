package com.linux.demo.controller;

import com.linux.demo.beans.Role;
import com.linux.demo.beans.User;
import com.linux.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void register(
            @RequestBody User user) {
        try {
            userService.register(user.getUsername(), user.getPassword(), user.getRole());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
