package com.example.login.vo;

import com.example.login.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录成功返回")
public class LoginVO {

    @Schema(description = "登录 token，前端存 localStorage")
    private String token;

    @Schema(description = "用户信息")
    private User user;
}