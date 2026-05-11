package com.example.login.controller;

import com.example.login.common.Result;
import com.example.login.entity.User;
import com.example.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "用户接口", description = "登录、注册相关接口")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户登录", description = "用户名密码登录")
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());

        if (loginUser != null) {
            return Result.success(loginUser);
        } else {
            return Result.error("用户名或密码错误！");
        }
    }

    @Operation(summary = "用户注册", description = "注册新用户，用户名不能重复")
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user){
        User newUser = userService.register(user.getUsername(),user.getPassword());
        if (newUser != null){
            return Result.success(newUser);
        }else {
            return Result.error("用户名已存在，注册失败");
        }
    }
}