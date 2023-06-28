package com.chuanqihou.powershop.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author 传奇后
 * @date 2023/6/26 19:21
 * @description web作用域工具类
 */
public class WebScopeUtil {

    /**
     * 获取HttpServletRequest对象
     * @return HttpServletRequest对象
     */
    public static HttpServletRequest getRequest() {
        // 获取RequestAttributes
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 从RequestAttributes中获取HttpServletRequest对象
        if (requestAttributes == null) {
            return null;
        }
        // 返回获取到的HttpServletRequest对象
        return requestAttributes.getRequest();
    }

}
