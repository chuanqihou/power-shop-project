package com.chuanqihou.powershop.util;

import com.chuanqihou.powershop.domain.LoginSysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * @author 传奇后
 * @date 2023/6/25 16:54
 * @description 获取当前登录用户信息
 */
public class AuthUtil {

    /**
     * 获取当前登录用户信息
     * @return 当前登录用户信息
     */
    public static LoginSysUser getLoginUser() {
        // 获取当前登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 返回当前登录用户信息
        return (LoginSysUser) authentication.getPrincipal();
    }

    /**
     * 获取当前登录用户id
     * @return 当前登录用户id
     */
    public static Long getLoginUserId() {
        // 返回当前登录用户id
        return getLoginUser().getUserId();
    }

    /**
     * 获取当前登录用户的权限
     * @return 当前登录用户的权限
     */
    public static Set<String> getLoginUserPermissions() {
        // 返回当前登录用户的权限
        return getLoginUser().getPerms();
    }

    public static Long getShopId() {
        return getLoginUser().getShopId();
    }

}
