package com.ciprianursulean.licenta.entities;

import lombok.Data;

@Data
public class User {
    private Integer userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public User(int user_id, String firstName, String lastName, String email, String password) {
        this.userId = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
