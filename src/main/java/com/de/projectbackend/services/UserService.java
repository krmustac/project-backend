package com.de.projectbackend.services;

import com.de.projectbackend.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user) throws Exception;

    List<User> getAllUsers() throws Exception;

    boolean deleteUser(Integer id) throws Exception;

    User getUserById(Integer id) throws Exception;

    boolean updateUser(Integer id, User user) throws Exception;

    User loginUser(User login) throws Exception;
}
