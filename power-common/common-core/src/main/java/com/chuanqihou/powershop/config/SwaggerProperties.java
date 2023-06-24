package com.chuanqihou.powershop.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 传奇后
 * @date 2023/6/24 14:14
 * @description swagger配置属性
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "swagger3")
public class SwaggerProperties {

    /**
     * 要对那个包的Controller 做文档的生成
     */
    private String basePackage;

    /**
     * 作者的名称
     */
    private String name;

    /**
     * 主页
     */
    private String url;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 服务的团队
     */
    private String termsOfServiceUrl;

    /**
     * 授权信息
     */
    private String license;

    /**
     * 授权的url
     */
    private String licenseUrl;

    /**
     * 版本
     */
    private String version;


}
