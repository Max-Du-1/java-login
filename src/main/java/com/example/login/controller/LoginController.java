package com.example.login.controller;

import com.example.login.common.PageResult;
import com.example.login.common.Result;
import com.example.login.entity.User;
import com.example.login.entity.UserQuery;
import com.example.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "用户接口", description = "登录、注册相关接口")
// 这一行就是规范核心！统一接口前缀
@RequestMapping("/api/user")
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

    /**
     * 分页查询用户列表：REST 惯例使用 GET；页码从 1 开始，默认每页 10 条。
     */
    @Operation(summary = "用户分页列表", description = "按页查询全部用户（按 id 排序）")
    @PostMapping("/page")
    public Result<PageResult<User>> pageUsers(@RequestBody UserQuery userQuery) {
        // 处理分页参数，给默认值
        Integer pageNum = userQuery.getPageNum();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }

        Integer pageSize = userQuery.getPageSize();
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        if (pageSize > 100) { // 限制最大条数，防止恶意请求
            pageSize = 100;
        }

        PageResult<User> pageResult = userService.pageUsers(pageNum, pageSize);
        return Result.success(pageResult);
    }
}