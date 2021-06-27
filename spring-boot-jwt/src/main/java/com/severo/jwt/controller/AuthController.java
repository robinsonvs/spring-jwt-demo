package com.severo.jwt.controller;

import com.severo.jwt.data.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody UserData userData) {
        userData.setPassword(bCryptPasswordEncoder.encode(userData.getPassword()));
    }
}
