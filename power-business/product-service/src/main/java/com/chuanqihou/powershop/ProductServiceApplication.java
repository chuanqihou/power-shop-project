package com.chuanqihou.powershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 传奇后
 * @date 2023/6/27 13:53
 * @description 商品模块启动类
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class ProductServiceApplication {

    /**
     * 启动类
     * @param args 参数
     */
    public static void main(String[] args) {
        //启动商品管理模块
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
