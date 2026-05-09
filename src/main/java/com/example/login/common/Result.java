package com.example.login.common;

import lombok.Data;

/**
 * 统一返回结果
 * 企业级标准JSON格式
 */
@Data
public class Result<T> {
    // 响应码：200成功 500失败
    private Integer code;
    // 响应消息
    private String msg;
    // 响应数据
    private T data;

    // 成功：无数据
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    // 成功：带数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 失败
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}