package com.example.login.service;

import com.example.login.common.PageResult;
import com.example.login.entity.User;

public interface UserService {
    User login(String username, String password);

    //注册
    User register(String username, String password);

    /** 用户列表分页查询，页码从 1 开始。 */
    PageResult<User> pageUsers(int pageNum, int pageSize);

    void deleteUser(Integer id);
}