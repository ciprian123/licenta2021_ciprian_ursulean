package com.ciprianursulean.licenta.services;

import com.ciprianursulean.licenta.exceptions.AuthException;
import com.ciprianursulean.licenta.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User validateUser(String email, String password) throws AuthException;
    User registerUser(String firstName, String lastName, String email, String password) throws AuthException;
}