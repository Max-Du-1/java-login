package com.example.login.controller;

import com.example.login.common.Result;
import com.example.login.entity.User;
import com.example.login.service.UserService;
import com.example.login.util.JwtUtil;
import com.example.login.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "登录接口", description = "登录相关接口")
@RequestMapping("/api/user")
public class LoginController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public LoginController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "用户登录username", description = "用户名密码登录，返回 token")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        return Result.success(buildLoginVO(loginUser));
    }

    @Operation(summary = "用户登录account", description = "账号密码登录，返回 token")
    @PostMapping("/loginnew")
    public Result<LoginVO> loginnew(@RequestBody User user) {
        User loginUser = userService.loginByAccount(user.getAccount(), user.getPassword());
        return Result.success(buildLoginVO(loginUser));
    }

    private LoginVO buildLoginVO(User user) {
        String token = jwtUtil.createToken(user.getUserId(), user.getUsername());
        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUser(user);
        return vo;
    }
}