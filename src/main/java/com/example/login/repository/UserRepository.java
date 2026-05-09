package com.example.login.repository;

import com.example.login.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    // 构造方法注入 JdbcTemplate（Spring 会自动给你实例化）
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 方法：根据用户名和密码查询用户
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(User.class),
                    username,
                    password
            );
        } catch (Exception e) {
            // 如果没查到用户，就返回 null
            return null;
        }
    }

    //注册用户
    public User findByUsername (String username){
        String sql = "select * from user where username = ?";
        try{
            return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                username
            );

        }catch(Exception e){
            return null;
        }
    }

    public void insertUser(String username,String password){
        String sql = "insert into user(username,password) values (?,?)";
        jdbcTemplate.update(sql,username,password);
    }
}