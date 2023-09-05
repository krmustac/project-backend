package com.de.projectbackend.services;

import com.de.projectbackend.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user) throws Exception;

    List<User> getAllUsers();

    boolean deleteUser(Integer id);

    User getUserById(Integer id);

    boolean updateUser(Integer id, User user);

    User loginUser(User login) throws Exception;
}
