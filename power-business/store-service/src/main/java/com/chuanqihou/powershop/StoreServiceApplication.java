package com.chuanqihou.powershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 传奇后
 * @date 2023/6/28 8:44
 * @description
 */
@SpringBootApplication
@EnableCaching
public class StoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class, args);
    }

}
