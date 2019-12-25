package com.linux.demo.service;

import com.linux.demo.beans.Role;
import com.linux.demo.beans.User;
import com.linux.demo.mongo.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDAO userDAO;

    public void register(String username, String password, Role role) throws Exception {
        boolean exists = userDAO.isExists(username);

        if (exists) throw new RuntimeException("Username already exists");

        User user = new User(null, username, passwordEncryptor.encrypt(password), role, true);
        userDAO.insertUser(user);
    }

    public String login(String username, String password) throws Exception {
        User user = userDAO.getByCredentialsEnabled(username, passwordEncryptor.encrypt(password), true);

        if (user == null) throw new RuntimeException("Username or Password invalid");

        return tokenService.createToken(user);
    }

    public void logout() {
        //TODO: Remove token from Redis
    }

    //TODO: refactor method to return boolean
    public void disable(String username) throws Exception {
        User user = userDAO.getOneByUsername(username);

        if (!user.isEnabled()) {
            user.setEnabled(false);
            boolean acknowledged = userDAO.update(user);
            if (!acknowledged) throw new RuntimeException("Connection to DB might be lost");
        }
    }

}
