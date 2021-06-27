package com.severo.jwt.controller;

import com.severo.jwt.data.UserData;
import com.severo.jwt.service.UserDetailServiceImp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserDetailServiceImp userDetailsService;

    public UserController(UserDetailServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/all-users")
    public List<UserData> listAllUsers() {
        return userDetailsService.listUsers();
    }
}
