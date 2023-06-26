package com.chuanqihou.powershop.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 传奇后
 * @date 2023/6/26 15:03
 * @description 密码加密工具类
 */
public class PasswordEncoderUtil {

    /**
     * 密码加密
     * @param password 密码
     * @return 加密后的密码
     */
    public static String passwordEncoder(String password) {
        //使用BCryptPasswordEncoder加密
        return new BCryptPasswordEncoder().encode(password);
    }

}
