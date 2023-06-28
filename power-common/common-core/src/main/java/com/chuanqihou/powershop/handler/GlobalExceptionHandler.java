package com.chuanqihou.powershop.handler;

import com.chuanqihou.powershop.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * 全局异常处理
     * @param ex 异常
     * @return Result
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result<Object> exceptionHandler(RuntimeException ex) {
        // 打印异常信息
        log.error("系统异常：{}", ex.getMessage());
        ex.printStackTrace();
        // 返回异常信息
        return Result.fails(500, "系统维护中，请稍后再试！");
    }

    /**
     * 参数异常处理
     * @param ex 异常
     * @return  Result
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> exceptionHandler(MethodArgumentNotValidException ex) {
        // 打印异常信息
        log.error("参数异常：{}", ex.getMessage());
        // 返回异常信息
        return Result.fails(400, "参数异常，请检查参数！");
    }

}
