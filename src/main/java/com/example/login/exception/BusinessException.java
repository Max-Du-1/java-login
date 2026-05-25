package com.example.login.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int code;
    private final String msg;

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}

/*
    把它想成一张错误纸条：
    BusinessException {
    code: 40001
    msg:  "用户名已存在"
}
*/