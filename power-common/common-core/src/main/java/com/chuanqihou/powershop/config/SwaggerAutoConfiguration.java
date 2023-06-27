package com.chuanqihou.powershop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

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
                .build() // 创建
                .securitySchemes(securitySchemes()) // token的全局配置
                .securityContexts(securityContexts());  // token的全局配置
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

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("Authorization", "Authorization", "header"));
        securitySchemes.add(new ApiKey("loginType", "loginType", "header"));
        return securitySchemes;
    }

    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        authorizationScopes[0] = authorizationScope;

        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        securityReferences.add(new SecurityReference("loginType", authorizationScopes));
        return securityReferences;
    }


}
