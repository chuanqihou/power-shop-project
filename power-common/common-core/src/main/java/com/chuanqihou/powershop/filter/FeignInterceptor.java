package com.chuanqihou.powershop.filter;

import com.chuanqihou.powershop.constant.AuthConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 传奇后
 * @date 2023/6/20 20:52
 * @description Feign拦截器
 */
public class FeignInterceptor implements RequestInterceptor {

    /**
     * Feign拦截器
     * @param requestTemplate 请求模板
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取请求属性
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!ObjectUtils.isEmpty(servletRequestAttributes)) {
            // 获取请求对象
            HttpServletRequest request = servletRequestAttributes.getRequest();
            // 获取请求头中的token
            String token = request.getHeader(AuthConstants.AUTHORIZATION);
            // 获取请求头中的loginType
            String loginType = request.getHeader(AuthConstants.LOGIN_TYPE);
            // 判断token和loginType是否为空
            if (!StringUtils.hasText(token) && !StringUtils.hasText(loginType)) {
                // 将token设置到请求头中
                requestTemplate.header(AuthConstants.AUTHORIZATION, token);
                // 将loginType设置到请求头中
                requestTemplate.header(AuthConstants.LOGIN_TYPE, loginType);
            }
        }
    }
}

