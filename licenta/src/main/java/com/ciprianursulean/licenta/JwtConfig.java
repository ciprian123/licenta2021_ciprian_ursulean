package com.ciprianursulean.licenta;

import com.ciprianursulean.licenta.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtConfig {
    public static final String API_SECRET_KEY = "predictionappsecretapikey1234";
    public static final int TOKEN_VALIDITY_PERIOD = 2 * 60 * 1000; // 1 hour per token

    public static Map<String, String> generateJwtToken(User user) {
        long currentTimestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, API_SECRET_KEY)
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + TOKEN_VALIDITY_PERIOD))
                .claim("user_id", user.getUserId())
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
