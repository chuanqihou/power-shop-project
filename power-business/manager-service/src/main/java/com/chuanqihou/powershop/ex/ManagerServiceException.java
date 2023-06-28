package com.chuanqihou.powershop.ex;

/**
 * @author 传奇后
 * @date 2023/6/28 10:35
 * @description 系统管理模块自定义异常
 */
public class ManagerServiceException extends RuntimeException{
    public ManagerServiceException() {
        super();
    }

    public ManagerServiceException(String message) {
        super(message);
    }
}
