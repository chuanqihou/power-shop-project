package com.chuanqihou.powershop.handler;

import com.chuanqihou.powershop.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.crypto.MarshalException;

/**
 * @author 传奇后
 * @date 2023/6/29 19:25
 * @description 处理 会员模块自定义异常
 */
@Slf4j
@RestControllerAdvice
public class MemberServiceExceptionHandler {

    @ExceptionHandler(value = MarshalException.class)
    public Result<Object> handlerMemberServiceException(MarshalException ex) {
        // 打印异常信息
        log.error("系统异常：{}", ex.getMessage());
        ex.printStackTrace();
        //返回
        return Result.fails(-1, ex.getMessage());
    }

}
