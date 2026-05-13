package com.example.login.controller;

import com.example.login.common.PageResult;
import com.example.login.common.Result;
import com.example.login.entity.User;
import com.example.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "用户管理", description = "用户查询、修改、删除")
@RequestMapping("/api/user")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户分页列表", description = "按页查询全部用户（按 id 排序）")
    @PostMapping("/page")
    public Result<PageResult<User>> pageUsers(@RequestBody User userBianliangmingkezidingyi) {
        // 处理分页参数，给默认值
        Integer pageNum = userBianliangmingkezidingyi.getPageNum();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }

        Integer pageSize = userBianliangmingkezidingyi.getPageSize();
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        if (pageSize > 100) { // 限制最大条数，防止恶意请求
            pageSize = 100;
        }

        PageResult<User> pageResult = userService.pageUsers(pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Operation(summary = "删除用户", description = "删除用户")
    @PostMapping("/delete")
    public Result<String> deleteUser(@RequestParam Integer id) {
        userService.deleteUser(id);
        return Result.success("success");
    }
}
