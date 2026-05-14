package com.example.login.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户实体类")
public class User {
    @JsonIgnore
    @Schema(description = "用户主键ID")
    private Integer id;
    @Schema(description = "登录用户名")
    private String username;
    @Schema(description = "登录密码")
    private String password;

    @Schema(description = "当前页码")
    private Integer pageNum;

    @Schema(description = "每页条数")
    private Integer pageSize;

    @JsonProperty("id")  // 关键：后端叫userId，前端返回变成 id
    @Schema(description = "用户对外唯一标识ID")
    private String userId;
}