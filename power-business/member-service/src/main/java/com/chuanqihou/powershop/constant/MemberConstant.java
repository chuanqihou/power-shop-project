package com.chuanqihou.powershop.constant;

/**
 * @author 传奇后
 * @date 2023/6/29 20:16
 * @description 会员模块常量类
 */
public class MemberConstant {
    /**
     * 短信验证码长度
     */
    public static final int SMS_CODE_DIGIT_LENGTH = 6;

    /**
     * 短信验证码缓存到redis中的key前缀
     */
    public static final String SMS_VERIFICATION_CODE = "SMS_VERIFICATION_CODE:";

    /**
     * 短信验证码存储在redis中的时间（默认为5分钟）单位：秒
     */
    public static final long SMS_CODE_EXIST_TIME = 300;

    /**
     * 发送短信验证码成功返回的值
     */
    public static final String SMS_SENT_OK = "OK";
    public static final String MEMBER_ADDR_CACHE_KEY_PREFIX = "member_addr_cache_key_prefix";
}
