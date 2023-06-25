package com.chuanqihou.powershop.constant;

/**
 * @author 传奇后
 * @date 2023/6/20 19:22
 * @description 资源常量
 */
public class ResourceConstant {

    /**
     * 资源允许访问的url
     */
    public static final String[] RESOURCE_ALLOW_URLS = {
            "/v2/api-docs/**", // swagger api json
            "/v3/api-docs/**",
            "/swagger-resources", // 用来获取api-docs的URI
            "/swagger-resources/configuration/ui", // 用来获取支持的动作
            "/swagger-resources/configuration/security", // 安全选项
            "/swagger-ui/**",
            "/webjars/**",
            "/druid/**", // druid监控
            "/actuator/**", // springboot监控
            "/doc.html"
    };

}
