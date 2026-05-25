package com.example.login.exception;

import com.example.login.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice       //= 我是全局异常捕手，整个项目的 Controller 出错都找我。
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class) //= 专门抓 BusinessException 这种错误。
    public Result<Void> handleBusinessException(BusinessException e) { //= 抓到了，e 就是扔出来的那张「错误纸条」。
        return Result.error(e.getCode(), e.getMsg());   //= 从纸条上读出 code 和 msg，包装成 Result 返回。
    }
}