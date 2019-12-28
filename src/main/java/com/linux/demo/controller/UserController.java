package com.linux.demo.controller;

import com.linux.demo.beans.User;
import com.linux.demo.service.TokenService;
import com.linux.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
//TODO remove later on!
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        try {
            userService.register(user.getUsername(), user.getPassword(), user.getRole());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /*
    FIXME token would be created even if the user info is wrong
     - a better way to get info could be the use of @PathVariable
     */
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        try {
            return userService.login(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


}
