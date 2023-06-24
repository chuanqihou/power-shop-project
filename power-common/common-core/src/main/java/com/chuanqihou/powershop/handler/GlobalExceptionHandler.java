package com.chuanqihou.powershop.handler;

import com.chuanqihou.powershop.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 传奇后
 * @date 2023/6/24 14:00
 * @description 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     * @param ex 自定义异常
     * @return Result
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result exceptionHandler(Exception ex) {
        log.error("系统异常：{}", ex.getMessage());
        return Result.fails(-1, "系统维护中，请稍后再试！");
    }

}
