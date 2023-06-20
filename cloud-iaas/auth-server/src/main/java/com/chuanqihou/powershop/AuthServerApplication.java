package com.chuanqihou.powershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 传奇后
 * @date 2023/6/19 20:23
 * @description 认证服务器
 */
@SpringBootApplication
public class AuthServerApplication {

    /**
     * 认证服务器启动类
     * @param args  参数
     */
    public static void main(String[] args) {
        // 启动认证服务器
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
