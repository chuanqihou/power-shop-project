package com.chuanqihou.powershop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author 传奇后
 * @date 2023/6/24 14:17
 * @description swagger自动配置类
 */
@Configuration
@EnableOpenApi
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    /**
     * swagger配置属性
     */
    @Autowired
    private SwaggerProperties swaggerProperties;

    /**
     * 创建swagger文档
     * @return swagger文档
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30). // 文档类型
                enable(true). // 是否启用
                apiInfo(getApiInfo()). // api信息
                select() // 选择api
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())) // 扫描路径
                .build(); // 创建
    }

    /**
     * 创建api信息
     * @return api信息
     */
    private ApiInfo getApiInfo() {
        // 联系人信息
        Contact contact = new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail());
        return new ApiInfoBuilder().    // 构建api信息
                contact(contact).   // 联系人信息
                title(swaggerProperties.getTitle()).    // 标题
                description(swaggerProperties.getDescription()).    // 描述
                termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl()).    // 服务条款
                license(swaggerProperties.getLicense()).    // 许可证
                licenseUrl(swaggerProperties.getLicenseUrl()).  // 许可证url
                version(swaggerProperties.getVersion()).    // 版本
                build();    // 创建
    }



}
