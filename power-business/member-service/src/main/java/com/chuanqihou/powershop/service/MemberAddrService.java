package com.chuanqihou.powershop.service;

import com.chuanqihou.powershop.domain.MemberAddr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface MemberAddrService extends IService<MemberAddr>{


    List<MemberAddr> findMemberAddrList(String loginMemberOpenId);

    void modifyDefaultAddr(Long addrId,String openId);

    void saveMemberAddr(MemberAddr memberAddr,String openId);

    void modifyMemberAddr(MemberAddr memberAddr,String openId);

    void removeSoftMemberAddrById(Long addrId,String openId);
}
