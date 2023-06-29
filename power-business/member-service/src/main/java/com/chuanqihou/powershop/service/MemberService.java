package com.chuanqihou.powershop.service;

import com.chuanqihou.powershop.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.dto.SMSCodeDTO;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface MemberService extends IService<Member>{


    void modifyMemberInfo(Member member);

    void modifyMemberAddPhone(SMSCodeDTO smsCodeDTO);
}
