package com.ciprianursulean.licenta.controllers;

import com.ciprianursulean.licenta.JwtConfig;
import com.ciprianursulean.licenta.entities.User;
import com.ciprianursulean.licenta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("first_name");
        String lastName  = (String) userMap.get("last_name");
        String email     = (String) userMap.get("email");
        String password  = (String) userMap.get("password");
        User newUser = userService.registerUser(firstName, lastName, email, password);
        return new ResponseEntity<>(JwtConfig.generateJwtToken(newUser), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(JwtConfig.generateJwtToken(user), HttpStatus.OK);
    }
}
