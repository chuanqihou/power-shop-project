package com.chuanqihou.powershop.service.impl;

import com.chuanqihou.powershop.constant.MemberConstant;
import com.chuanqihou.powershop.dto.SMSCodeDTO;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Member;
import com.chuanqihou.powershop.mapper.MemberMapper;
import com.chuanqihou.powershop.service.MemberService;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void modifyMemberInfo(Member member) {
        Integer loginMemberId = AuthUtil.getLoginMemberId();
        member.setId(loginMemberId);
        member.setUpdateTime(new Date());
        int updateMemberInfo = memberMapper.updateById(member);
        if (updateMemberInfo != 1) {
            throw new RuntimeException("更新用户头像失败，请联系管理员！");
        }
    }

    @Override
    public void modifyMemberAddPhone(SMSCodeDTO smsCodeDTO) {
        String redisKey = MemberConstant.SMS_VERIFICATION_CODE + smsCodeDTO.getPhonenum();
        String rightCode = stringRedisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.hasText(rightCode)) {
            throw new RuntimeException("请输入验证码！");
        }
        stringRedisTemplate.delete(redisKey);
        String userCode = smsCodeDTO.getCode();
        if (!rightCode.equals(userCode)) {
            throw new RuntimeException("验证码错误！");
        }
        Member member = new Member();
        member.setId(AuthUtil.getLoginMemberId());
        member.setUserMobile(smsCodeDTO.getPhonenum());
        memberMapper.updateById(member);
    }
}
