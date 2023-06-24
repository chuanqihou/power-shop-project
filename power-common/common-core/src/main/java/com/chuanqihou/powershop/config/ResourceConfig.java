package com.chuanqihou.powershop.config;

import com.chuanqihou.powershop.constant.ResourceConstant;
import com.chuanqihou.powershop.filter.TokenTranslateFilter;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 传奇后
 * @date 2023/6/20 19:10
 * @description 资源配置
 */
@Configuration
public class ResourceConfig extends WebSecurityConfigurerAdapter {

    /**
     * token转换过滤器
     */
    @Autowired
    private TokenTranslateFilter tokenTranslateFilter;

    /**
     * 配置资源
     * @param http http安全配置
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();
        // 添加自定义过滤器（在执行登录认证过滤器之前执行）
        http.addFilterBefore(tokenTranslateFilter, UsernamePasswordAuthenticationFilter.class);
        // 配置异常处理
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        // 配置资源
        http.authorizeRequests()
                .antMatchers(ResourceConstant.RESOURCE_ALLOW_URLS)  // 允许访问的资源
                .permitAll()   // 允许访问
                .anyRequest()   // 任何请求
                .authenticated();   // 都需要认证
    }

    /**
     * 配置拒绝访问处理器
     * @return 拒绝访问处理器
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        // 返回拒绝访问处理器
        return (request, response, ex) -> {
            // 返回json数据
            ResponseUtil.responseJson(response, Result.fails(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
        };
    }

}
