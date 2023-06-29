package com.chuanqihou.powershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 传奇后
 * @date 2023/6/29 10:54
 * @description 发送http请求模板对象配置类
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 发送rest风格的模板对象
     * @return 模板对象
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
