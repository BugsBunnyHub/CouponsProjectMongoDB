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

import java.util.Arrays;

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


    /*
    git branch - check what branch you are working on (locally)

    git checkout -b branchname - creates new branch
    git push orgin branch name - to create it
    git add .
    git commit -m "comments"
    git push orgin branch name
    git branch -D branchname
    git pull origin master

    use this if you are behind master
    git merge origin master
    */

    @Override
    public void run(String ...args) throws Exception {

//        User user1 = new User(null, "daniel", "123456", Role.ADMIN);
//        User user2 = new User(null, "vlad", "444555", Role.CUSTOMER);
//        User user3 = new User(null, "moshe", "888555", Role.COMPANY);

//        Arrays.asList(user1, user2, user3).forEach(u -> {
//            try {
//                userDAO.insertUser(u);
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//                throw new RuntimeException(e.getMessage());
//            }
//        });

//        userDAO.getAll().forEach(u -> logger.info(u.toString()));
//        User fetchedUser = userDAO.getByCredentials(user1.getUsername(), user1.getPassword());
//        System.out.println(fetchedUser);
//        System.out.println(userDAO.getOne(fetchedUser.getId()));
//        System.out.println(userDAO.DeleteUser(fetchedUser.getId()));
//        User updateUser = userDAO.getByCredentials(user2.getUsername(), user2.getPassword());
//        updateUser.setRole(Role.ADMIN);
//        System.out.println(userDAO.update(updateUser, updateUser.getId()));
//        System.out.println("WORKS");

    }
}
