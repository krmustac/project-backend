package com.de.projectbackend;

import com.de.projectbackend.model.User;
import com.de.projectbackend.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest{

    @Autowired
    UserServiceImpl userService;
    @Test
    public void testCreateUser() throws Exception {
        User user = new User();

        user.setPassword("krvavica");

        User result = userService.createUser(user);
    }
}
