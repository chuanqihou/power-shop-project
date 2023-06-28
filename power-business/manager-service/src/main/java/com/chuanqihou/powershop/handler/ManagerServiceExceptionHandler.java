package com.chuanqihou.powershop.handler;

import com.chuanqihou.powershop.ex.ManagerServiceException;
import com.chuanqihou.powershop.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 传奇后
 * @date 2023/6/28 10:39
 * @description 系统管理模块自定义异常处理
 */
@Slf4j
@RestControllerAdvice
public class ManagerServiceExceptionHandler {

    /**
     * 处理ManagerServiceException异常
     * @param ex ManagerServiceException异常
     * @return Result
     */
    @ExceptionHandler(value = ManagerServiceException.class)
    public Result<Object> manageServiceExceptionHandler(ManagerServiceException ex) {
        // 打印异常信息
        log.error("系统异常：{}", ex.getMessage());
        ex.printStackTrace();
        // 返回异常信息
        return Result.fails(-1, ex.getMessage());
    }

}
