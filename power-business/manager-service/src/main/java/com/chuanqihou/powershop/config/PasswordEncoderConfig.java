package com.chuanqihou.powershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 传奇后
 * @date 2023/6/26 15:02
 * @description 密码编码器配置类
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * 配置密码编码器
     *
     * @return 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密
        return new BCryptPasswordEncoder();
    }

}
