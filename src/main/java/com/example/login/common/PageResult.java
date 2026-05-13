package com.example.login.common;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * 通用分页结果：与 {@link Result} 配合，作为 data 承载列表与分页元信息。
 *
 * @param <T> 列表元素类型
 */
@Data
@Schema(description = "分页结果")
public class PageResult<T> {

    @Schema(description = "当前页数据列表")
    private List<T> list;

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "当前页码，从 1 开始")
    private Integer pageNum;

    @Schema(description = "每页条数")
    private Integer pageSize;
}
