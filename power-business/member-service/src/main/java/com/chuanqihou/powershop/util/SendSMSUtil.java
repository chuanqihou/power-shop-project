package com.chuanqihou.powershop.util;

import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.provider.enumerate.SupplierType;
/**
 * @author 传奇后
 * @date 2023/6/29 15:26
 * @description 发送短信工具类
 */
public class SendSMSUtil {

    /**
     * 发送短信方法
     * @param phone 手机号
     * @param code 验证码
     * @return 响应数据
     */
    public static SmsResponse sendSMSVerificationCode(String phone, String code) {
        // 发送短信
        return SmsFactory.createSmsBlend(SupplierType.ALIBABA).sendMessage(phone, code);
    }

}
