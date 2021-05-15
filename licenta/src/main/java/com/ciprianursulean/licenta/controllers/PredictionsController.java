package com.ciprianursulean.licenta.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/predictions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PredictionsController {
    @GetMapping("")
    public String getTestMessage(HttpServletRequest httpServletRequest) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        return "User id = " + userId;
    }
}
