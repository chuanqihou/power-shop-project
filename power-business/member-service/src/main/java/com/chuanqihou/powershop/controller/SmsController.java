package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.constant.MemberConstant;
import com.chuanqihou.powershop.dto.SMSCodeDTO;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.MemberService;
import com.chuanqihou.powershop.util.SendSMSUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.comm.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author 传奇后
 * @date 2023/6/29 15:21
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/p/sms")
public class SmsController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MemberService memberService;

    @PostMapping("/send")
    public Result<String> sendSMSVerificationCode(@RequestBody SMSCodeDTO smsCodeDTO) {
        // 获取随机 n 位数字字符串
        String code = SmsUtil.getRandomInt(MemberConstant.SMS_CODE_DIGIT_LENGTH);
        // 发送短信验证码
        SmsResponse smsResponse = SendSMSUtil.sendSMSVerificationCode(smsCodeDTO.getPhonenum(), code);
        // 将短信验证码存储在redis中
        stringRedisTemplate.opsForValue().set(MemberConstant.SMS_VERIFICATION_CODE + smsCodeDTO.getPhonenum(), code, MemberConstant.SMS_CODE_EXIST_TIME, TimeUnit.SECONDS);
        log.info("验证码：" + code);
        // 返回数据
        if (MemberConstant.SMS_SENT_OK.equals(smsResponse.getMessage())) {
            return Result.success("发送成功！");
        }
        return Result.fails("短信验证码发送失败，请联系管理员！");
    }


    @PostMapping("/savePhone")
    public Result<Object> saveMemberPhone(@RequestBody SMSCodeDTO smsCodeDTO) {
        memberService.modifyMemberAddPhone(smsCodeDTO);
        return Result.success();
    }

}
