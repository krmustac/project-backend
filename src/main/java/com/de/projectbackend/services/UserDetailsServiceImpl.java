package com.de.projectbackend.services;

import com.de.projectbackend.model.User;
import com.de.projectbackend.model.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserServiceImpl userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        user.setUsername(username);
        final User verifiedUserByUsername = userService.getUserByUsername(user.getUsername());
        if(verifiedUserByUsername != null && verifiedUserByUsername.getId() == null){
            return null;
        }
        return UserDetailsImpl.build(verifiedUserByUsername);
    }
}
