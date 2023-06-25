package com.chuanqihou.powershop.config;

import com.alibaba.fastjson.JSON;
import com.chuanqihou.powershop.constant.AuthConstants;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.UserDetailsServiceImpl;
import com.chuanqihou.powershop.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 传奇后
 * @date 2023/6/19 20:26
 * @description 认证服务器安全配置
 */
@Configuration
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * redis操作模板
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 配置认证管理器 用于认证用户的身份
     *
     * @param auth 认证管理器构建器
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置认证管理器的用户信息服务
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 配置请求的相关配置
     *
     * @param http http安全对象
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * http.csrf().disable()的作用是禁用跨站请求伪造（CSRF）保护。
         * CSRF是一种攻击方式，通过伪造用户的请求来执行恶意操作。通常，应用程序需要防止CSRF攻击，
         * 但在某些情况下，可能会选择禁用CSRF保护。
         * 通过调用csrf().disable()，这段代码关闭了CSRF保护，使应用程序在处理请求时不会验证请求的CSRF令牌。
         */
        http.csrf().disable();

        // 设置登录的路径
        http.formLogin()
                .loginProcessingUrl(AuthConstants.LOGIN_URL)    // 登录请求的路径
                .successHandler(authenticationSuccessHandler()) // 登录成功处理器
                .failureHandler(authenticationFailureHandler()) // 登录失败处理器
                .permitAll();                                // 允许所有用户访问登录路径

        // 设置登出的路径
        http.logout()
                .logoutUrl(AuthConstants.LOGIN_OUT_URL) // 登出请求的路径
                .logoutSuccessHandler(logoutSuccessHandler()) // 登出成功处理器
                .permitAll();                               // 允许所有用户访问登出路径

        // 设置请求的权限
        http.authorizeRequests() // 对请求进行授权
                .antMatchers(AuthConstants.MONITOR_URL) // 监控中心的请求路径
                .permitAll() // 允许所有用户访问监控中心
                .anyRequest() // 任何请求
                .authenticated();  // 需要身份认证

    }

    /**
     * 配置登录成功处理器
     *
     * @return 登录成功处理器
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 生成token
            String token = UUID.randomUUID().toString().replace("-", "");
            // 将token存放到redis
            String redisKey = AuthConstants.TOKEN_REDIS_PREFIX + token;
            String redisValue = JSON.toJSONString(authentication);
            //Duration.ofSeconds();
            redisTemplate.opsForValue().set(redisKey, redisValue, AuthConstants.TOKEN_EXPIRE, AuthConstants.TOKEN_EXPIRE_TIME_UNIT);
            // 封装数据
            Map<String, Object> map = new HashMap<>();
            map.put(AuthConstants.ACCESS_TOKEN, token);
            map.put(AuthConstants.EXPIRES_IN, AuthConstants.TOKEN_EXPIRE);
            map.put(AuthConstants.TYPE, AuthConstants.BEARER);
            // 返回token
            ResponseUtil.responseJson(response, map);
        };
    }

    /**
     * 配置登录失败处理器
     *
     * @return 登录失败处理器
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, e) -> {
            Result result = new Result();
            result.setCode(HttpStatus.UNAUTHORIZED.value());

            if (e instanceof LockedException) {
                result.setMsg("账户被锁定，请联系管理员!");
            } else if(e instanceof BadCredentialsException){
                result.setMsg("用户名或密码错误，请重新登录！");
            }else  if(e instanceof DisabledException){
                result.setMsg("账户被禁用，请联系管理员！");
            }else if(e instanceof AccountExpiredException){
                result.setMsg("账户过期，请联系管理员！");
            }else if(e instanceof CredentialsExpiredException){
                result.setMsg("密码过期，请联系管理员！");
            }else{
                result.setMsg("登录失败！");
            }
            // 响应
            ResponseUtil.responseJson(response,result);
        };
    }

    /**
     * 配置登出成功处理器
     *
     * @return 登出成功处理器
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            //获取token
            String auth = request.getHeader("Authorization");
            //判断是否有token
            if (StringUtils.hasText(auth)){
                String token = auth.replace(AuthConstants.BEARER, "").trim();
                //从redis中删除
                redisTemplate.delete(AuthConstants.TOKEN_REDIS_PREFIX + token);
            }
            //返回数据
            ResponseUtil.responseJson(response,Result.success());
        };
    }

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
