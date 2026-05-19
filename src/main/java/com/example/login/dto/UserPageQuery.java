package com.example.login.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户分页查询参数")
public class UserPageQuery {

    @Schema(description = "当前页码，从 1 开始", example = "1")
    private Integer pageNum;
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize;
}
