package com.chuanqihou.powershop.ex;

/**
 * @author 传奇后
 * @date 2023/6/29 19:25
 * @description 会员模块自定义异常
 */
public class MemberServiceException extends RuntimeException{
    public MemberServiceException() {
        super();
    }

    public MemberServiceException(String message) {
        super(message);
    }
}
