package com.chuanqihou.powershop.handler;

import com.chuanqihou.powershop.ex.ProductServiceException;
import com.chuanqihou.powershop.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 传奇后
 * @date 2023/6/28 14:12
 * @description 处理产品管理模块自定义异常
 */
@Slf4j
@RestControllerAdvice
public class ProductServiceExceptionHandler {

    /**
     * 处理ProductServiceException异常
     * @param ex ProductServiceException异常
     * @return result
     */
    @ExceptionHandler(value = ProductServiceException.class)
    public Result<Object> productServiceExHandler(ProductServiceException ex) {
        // 打印异常信息
        log.error("系统异常：{}", ex.getMessage());
        ex.printStackTrace();
        //返回
        return Result.fails(-1, ex.getMessage());
    }

}
