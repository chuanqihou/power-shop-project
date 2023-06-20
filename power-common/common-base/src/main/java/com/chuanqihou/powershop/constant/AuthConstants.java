package com.chuanqihou.powershop.constant;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 传奇后
 * @date 2023/6/19 19:06
 * @description 认证相关的常量
 */
public class AuthConstants {

    /**
     * 需要放行的路径
     */
    public static final List<String> ALLOW_URLS = Arrays.asList("/auth-server/doLogin","/doLogin");

    /**
     * token所在请求头
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 持票人的 token前缀
     */
    public static final String BEARER = "Bearer ";

    /**
     * 存在redis的token前缀
     */
    public static final String TOKEN_REDIS_PREFIX = "login_prefix:";

    /**
     * token过期时间（默认 30 分钟）
     */
    public static final long TOKEN_EXPIRE = 30L;

    /**
     * token过期时间单位（默认分钟）
     */
    public static final TimeUnit TOKEN_EXPIRE_TIME_UNIT = TimeUnit.MINUTES;

    /**
     * 登录地址
     */
    public static final String LOGIN_URL = "/doLogin";

    /**
     * 登出地址
     */
    public static final String LOGIN_OUT_URL = "/doLogout";

    /**
     * 监控地址
     */
    public static final String[] MONITOR_URL = {"/actuator","/druid/**"};

    /**
     * 登录类型
     */
    public static final String LOGIN_TYPE = "loginType";

    /**
     * 后台用户
     */
    public static final String SYS_USER = "sysUser";

    /**
     * 微信用户
     */
    public static final String WX_USER = "wx_user";

    /**
     * token
     */
    public static final String ACCESS_TOKEN = "accessToken";

    /**
     * 过期时间
     */
    public static final String EXPIRES_IN = "expiresIn";

    /**
     * 登录类型
     */
    public static final String TYPE = "type";
}
