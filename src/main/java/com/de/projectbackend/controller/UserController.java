package com.de.projectbackend.controller;

import com.de.projectbackend.config.UserAuthProvider;
import com.de.projectbackend.model.User;
import com.de.projectbackend.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    private final UserAuthProvider userAuthProvider;

    public UserController(UserServiceImpl userService, UserAuthProvider userAuthProvider){
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) throws Exception {
        User result = userService.createUser(user);
        result.setToken(userAuthProvider.createToken(result.getUsername()));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable @PositiveOrZero Integer id)
            throws Exception {
        boolean deleted = false;
        deleted = userService.deleteUser(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @PositiveOrZero Integer id) throws Exception {
        User user = null;
        user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public boolean updateUser(@PathVariable Integer id, @RequestBody @Valid User user) throws Exception {
        userService.updateUser(id,user);
        return userService.updateUser(id,user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> userLogin(@RequestBody @Valid User login) throws Exception {
        User user= userService.loginUser(login);
        user.setToken(userAuthProvider.createToken(user.getUsername()));
        return ResponseEntity.ok(user);
    }

}
