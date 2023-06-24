package com.chuanqihou.powershop.util;

import org.springframework.util.AntPathMatcher;

/**
 * @author 传奇后
 * @date 2023/6/20 20:34
 * @description 自定义匹配器
 */
public class MyMatchers {

    /**
     * 判断是否匹配
     * @param path 请求路径
     * @param allowPaths 允许的路径
     * @return 是否匹配
     */
    public static boolean isMatch(String path,String[] allowPaths) {
        // 创建AntPathMatcher对象
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 遍历允许的路径
        for (String allowPath  : allowPaths) {
            // 判断是否匹配
            boolean match = antPathMatcher.match(allowPath, path);
            if (match) {
                // 匹配成功，返回true
                return true;
            }
        }
        // 匹配失败，返回false
        return false;
    }
}
