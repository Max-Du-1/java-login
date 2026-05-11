package com.example.login.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户实体类")
public class User {
    @Schema(description = "用户主键ID")
    private Integer id;
    @Schema(description = "登录用户名")
    private String username;
    @Schema(description = "登录密码")
    private String password;
}