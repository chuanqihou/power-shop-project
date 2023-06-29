package com.chuanqihou.powershop.ex;

/**
 * @author 传奇后
 * @date 2023/6/29 19:33
 * @description 门店模块 自定义异常
 */
public class StoreServiceException extends RuntimeException{

    public StoreServiceException() {
        super();
    }

    public StoreServiceException(String message) {
        super(message);
    }
}
