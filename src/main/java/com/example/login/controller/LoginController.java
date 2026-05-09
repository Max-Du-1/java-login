package com.example.login.controller;

import com.example.login.pojo.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final JdbcTemplate jdbcTemplate;

    // 构造方法注入 JdbcTemplate
    public LoginController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            // 用 JdbcTemplate 直接查询云数据库
            jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class),
                    user.getUsername(),
                    user.getPassword()
            );
            return "登录成功！（数据来自你的云MySQL）";
        } catch (Exception e) {
            return "用户名或密码错误！";
        }
    }
}