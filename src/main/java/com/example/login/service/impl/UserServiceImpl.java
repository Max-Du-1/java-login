package com.example.login.service.impl;

import com.example.login.entity.User;
import com.example.login.repository.UserRepository;
import com.example.login.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 注入 repository 层
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        // 调用 repository 层的方法，完成业务逻辑
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User register(String username,String password){
        User oldUser = userRepository.findByUsername(username);
        if(oldUser != null){
            return null;
        }
        userRepository.insertUser(username,password);
        return userRepository.findByUsername(username);
    }

}