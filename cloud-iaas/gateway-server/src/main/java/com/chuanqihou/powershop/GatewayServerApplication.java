package com.chuanqihou.powershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 传奇后
 * @date 2023/6/19 18:40
 * @description 网关服务
 */
@SpringBootApplication
public class GatewayServerApplication {

    /**
     * 网关服务启动入口
     * @param args
     */
    public static void main(String[] args) {
        // 启动网关服务
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
