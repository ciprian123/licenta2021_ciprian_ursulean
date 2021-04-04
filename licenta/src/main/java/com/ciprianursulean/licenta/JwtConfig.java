package com.ciprianursulean.licenta;

import com.ciprianursulean.licenta.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtConfig {
    public static final String API_SECRET_KEY = "predictionappsecretapikey1234";
    public static final int TOKEN_VALIDITY_PERIOD = 5 * 60 * 100000; // 5 hour per token

    public static Map<String, String> generateJwtToken(User user) {
        long currentTimestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, API_SECRET_KEY)
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + TOKEN_VALIDITY_PERIOD))
                .claim("userId", user.getUserId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
