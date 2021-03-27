package com.ciprianursulean.licenta.services.impl;

import com.ciprianursulean.licenta.exceptions.AuthException;
import com.ciprianursulean.licenta.entities.User;
import com.ciprianursulean.licenta.repositories.UserRepository;
import com.ciprianursulean.licenta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws AuthException {
        if (email != null) {
            email = email.toLowerCase();
        }
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws AuthException {
        Pattern validateEmailPattern = Pattern.compile("^(.+)@(.+)$");
        if (email != null) {
            email = email.toLowerCase();
        }
        if (!validateEmailPattern.matcher(email).matches()) {
            throw new AuthException("The email is not valid! Please enter a valid format!");
        }
        boolean emailAlreadyUsed = userRepository.checkIfEmailExists(email);
        if (emailAlreadyUsed) {
            throw new AuthException("The email is already used by an existing account! Please log in!");
        }
        Integer userId = userRepository.createUser(firstName, lastName, email, password);
        return userRepository.findUserById(userId);
    }
}
