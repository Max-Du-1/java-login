package com.example.login.controller;

import com.example.login.common.PageResult;
import com.example.login.common.Result;
import com.example.login.entity.User;
import com.example.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "登录接口", description = "登录相关接口")
// 这一行就是规范核心！统一接口前缀
@RequestMapping("/api/user")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户登录username", description = "用户名密码登录")
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());

        if (loginUser != null) {
            return Result.success(loginUser);
        } else {
            return Result.error("用户名或密码错误！");
        }
    }



    @Operation(summary = "用户登录account", description = "账号密码登录")
    @PostMapping("/loginnew")
    public Result<User> loginnew(@RequestBody User user) {
        User loginUser = userService.loginByAccount(user.getAccount(), user.getPassword());

        if (loginUser != null) {
            return Result.success(loginUser);
        } else {
            return Result.error("用户名或密码错误！");
        }
    }


}

