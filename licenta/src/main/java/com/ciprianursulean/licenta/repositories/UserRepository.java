package com.ciprianursulean.licenta.repositories;

import com.ciprianursulean.licenta.exceptions.AuthException;
import com.ciprianursulean.licenta.entities.User;

public interface UserRepository {
    Integer createUser(String firstName, String lastName, String email, String password) throws AuthException;
    User findUserByEmailAndPassword(String email, String password) throws AuthException;
    Boolean checkIfEmailExists(String email);
    User findUserById(Integer userId);
}
