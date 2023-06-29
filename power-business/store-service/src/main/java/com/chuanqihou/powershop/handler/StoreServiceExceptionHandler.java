package com.chuanqihou.powershop.handler;

import com.chuanqihou.powershop.ex.StoreServiceException;
import com.chuanqihou.powershop.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 传奇后
 * @date 2023/6/29 19:34
 * @description
 */
@Slf4j
@RestControllerAdvice
public class StoreServiceExceptionHandler {

    @ExceptionHandler(value = StoreServiceException.class)
    public Result<Object> handlerStoreServiceException(StoreServiceException ex) {
        // 打印异常信息
        log.error("系统异常：{}", ex.getMessage());
        ex.printStackTrace();
        //返回
        return Result.fails(-1, ex.getMessage());
    }

}
