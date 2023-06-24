package com.chuanqihou.powershop.filter;

import com.alibaba.fastjson.JSON;
import com.chuanqihou.powershop.constant.AuthConstants;
import com.chuanqihou.powershop.constant.ResourceConstant;
import com.chuanqihou.powershop.domain.LoginSysUser;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.util.MyMatchers;
import com.chuanqihou.powershop.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 传奇后
 * @date 2023/6/20 19:09
 * @description token转换过滤器
 */
@Component
public class TokenTranslateFilter extends OncePerRequestFilter {

    /**
     * redis操作模板
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 执行过滤器
     * @param request   请求
     * @param response  响应
     * @param filterChain   过滤器链
     * @throws ServletException servlet异常
     * @throws IOException  IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求路径
        String path = request.getRequestURI();
        // 判断是否为放行路径
        if (MyMatchers.isMatch(path, ResourceConstant.RESOURCE_ALLOW_URLS)){
            // 如果为放行路径，直接放行
            filterChain.doFilter(request, response);
            return;
        }
        // 获取登录类型
        String loginType = request.getHeader(AuthConstants.LOGIN_TYPE);
        if (!StringUtils.hasText(loginType)) {
            ResponseUtil.responseJson(response, Result.fails(HttpStatus.FORBIDDEN.value(), "非法访问，登录类型不能为空"));
        }
        // 获取token
        String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
        if (!StringUtils.hasText(authorization)) {
            ResponseUtil.responseJson(response, Result.fails(HttpStatus.UNAUTHORIZED.value(), "非法访问，token不能为空"));
        }
        String token = authorization.replaceFirst(AuthConstants.BEARER, "");
        // 从redis中获取token对应的用户信息
        String redisKey = AuthConstants.TOKEN_REDIS_PREFIX + token;
        String userJson = redisTemplate.opsForValue().get(redisKey);
        // 判断是否为空
        if (!StringUtils.hasText(userJson)) {
            // 为空，返回错误信息
            ResponseUtil.responseJson(response, Result.fails(HttpStatus.UNAUTHORIZED.value(), "token非法"));
        }
        //获取token过期时间
        Long expire = redisTemplate.getExpire(redisKey);
        // 判读token是否过期以及是否需要刷新token过期时间
        if (!ObjectUtils.isEmpty(expire) && expire != -2 && expire < AuthConstants.TOKEN_REFRESH_EXPIRE_TIME) {
            //如果token存在,并且token过期时间小于5分钟，则刷新token时间
            redisTemplate.expire(redisKey, AuthConstants.TOKEN_EXPIRE, AuthConstants.TOKEN_EXPIRE_TIME_UNIT);
        }
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken upa = null;
        // 判断登录类型
        switch (loginType) {
            case AuthConstants.LOGIN_TYPE:
                // 获取 UsernamePasswordAuthenticationToken 对象
                upa = JSON.parseObject(userJson, UsernamePasswordAuthenticationToken.class);
                // 判断是否为空
                if (!ObjectUtils.isEmpty(upa)) {
                    // 获取用户信息
                    LoginSysUser loginSysUser = JSON.parseObject(upa.getPrincipal().toString(), LoginSysUser.class);
                    // 获取用户权限
                    Set<String> perms = loginSysUser.getPerms();
                    Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
                    perms.forEach(perm -> {
                        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(perm));
                    });
/*                    List<SimpleGrantedAuthority> simpleGrantedAuthorities = perms.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());*/

                    // 创建认证信息
                    authentication = new UsernamePasswordAuthenticationToken(loginSysUser, null, simpleGrantedAuthorities);
                }
                break;
        }
        // 设置认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
