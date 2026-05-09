package com.example.login.service;

import com.example.login.entity.User;

public interface UserService {
    User login(String username, String password);
}