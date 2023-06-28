package com.chuanqihou.powershop.ex;

/**
 * @author 传奇后
 * @date 2023/6/28 14:11
 * @description 产品管理模块自定义异常类
 */
public class ProductServiceException extends RuntimeException{
    public ProductServiceException() {
        super();
    }

    public ProductServiceException(String message) {
        super(message);
    }
}
