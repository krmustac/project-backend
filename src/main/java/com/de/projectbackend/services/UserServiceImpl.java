package com.de.projectbackend.services;

import com.de.projectbackend.model.LoginMessage;
import com.de.projectbackend.model.User;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserServiceImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @NotNull
    private RowMapper<User> getUserRowMapper(){
        return ((rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRoleId(rs.getObject("roleId", Integer.class));
            return user;
        });
    }

    @Override
    public User createUser(User user) throws Exception {

        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("username", user.getUsername());
            paramMap.put("password", passwordEncoder.encode(user.getPassword()));

            boolean existingUser = getUserByUsername(user.getUsername()) != null;
            final String sql = """
                INSERT INTO users (
                username,
                password,
                roleId)
                VALUES (
                :username,
                :password,
                2)
                """;

        if(existingUser){
            throw new Exception("User already exists");
        }

        boolean created = jdbcTemplate.update(sql,paramMap)>0;
        if(created){
            User result = getUserByUsername(user.getUsername());
            return result;
        }
        return null;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            final String sql = """
                SELECT * FROM users
                """;

        return jdbcTemplate.query(sql,paramMap,getUserRowMapper());
        } catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public boolean deleteUser(Integer id) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);

            final String sql = """
                DELETE FROM users WHERE id = :id
                """;

        return jdbcTemplate.update(sql,paramMap) > 0;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public User getUserById(Integer id) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            final String sql = """
                SELECT * FROM users WHERE id = :id
                """;

        List<User> users = jdbcTemplate.query(sql,paramMap,getUserRowMapper());
        return users.size() > 0 ? users.get(0) : null;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }


    public User getUserByUsername(String username) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("username", username);
            final String sql = """
                SELECT * FROM users WHERE username = :username
                """;

        List<User> users = jdbcTemplate.query(sql,paramMap,getUserRowMapper());
        return users.size() > 0 ? users.get(0) : null;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public boolean updateUser(Integer id, User user) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            paramMap.put("roleId", user.getRoleId());

            final String sql = """
                UPDATE users
                SET
                roleId = :roleId
                WHERE id = :id
                """;

        return jdbcTemplate.update(sql,paramMap) > 0;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public User loginUser(User login) throws Exception {

        User user = getUserByUsername(login.getUsername());
        if(user!=null){
            String password = login.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password,encodedPassword);
            if (isPwdRight){
                return user;
            }
            else throw new Exception("Invalid password");
        }
        throw new Exception("Invalid username");
    }

}
