package com.example.login.controller;

import com.example.login.common.Result;
import com.example.login.entity.User;
import com.example.login.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());

        if (loginUser != null) {
            // 标准成功返回：带用户数据
            return Result.success(loginUser);
        } else {
            // 标准失败返回
            return Result.error("用户名或密码错误！");
        }
    }
}