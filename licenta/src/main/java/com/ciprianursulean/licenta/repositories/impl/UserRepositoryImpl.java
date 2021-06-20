package com.ciprianursulean.licenta.repositories.impl;

import com.ciprianursulean.licenta.exceptions.AuthException;
import com.ciprianursulean.licenta.entities.User;
import com.ciprianursulean.licenta.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;


@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_CREATE_USER = "INSERT INTO USERS(first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT COUNT(user_id) FROM USERS WHERE email = ?";
    private static final String SQL_GET_USER_BY_EMAIL = "SELECT user_id, first_name, last_name, email, password FROM USERS WHERE email = ?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT user_id, first_name, last_name, email, password FROM USERS WHERE user_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer createUser(String firstName, String lastName, String email, String password) throws AuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(16));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, hashedPassword);
                return preparedStatement;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("user_id");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new AuthException("Error while creating your account! Invalid details");
        }
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws AuthException {
        try {
            User userFound = jdbcTemplate.queryForObject(SQL_GET_USER_BY_EMAIL, new Object[]{email}, userRowMapper);
            if (userFound != null && !BCrypt.checkpw(password, userFound.getPassword())) {
                throw new AuthException("Invalid email or password!");
            }
            return userFound;
        } catch (EmptyResultDataAccessException exception) {
            throw new AuthException("Invalid email or password!");
        }
    }

    @Override
    public Boolean checkIfEmailExists(String email) {
        Integer counter = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_EMAIL, new Object[]{email}, Integer.class);
        System.out.println("counter = " + counter);
        return counter != null && counter > 0;
    }

    @Override
    public User findUserById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, new Object[]{userId}, userRowMapper);
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"));
}
