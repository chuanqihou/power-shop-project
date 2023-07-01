package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.constant.MemberConstant;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.mapper.MemberAddrMapper;
import com.chuanqihou.powershop.domain.MemberAddr;
import com.chuanqihou.powershop.service.MemberAddrService;
import org.springframework.util.ObjectUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 会员管理业务实现类
 */
@Service
@CacheConfig(cacheNames = "com.chuanqihou.powershop.service.impl.MemberAddrServiceImpl")
public class MemberAddrServiceImpl extends ServiceImpl<MemberAddrMapper, MemberAddr> implements MemberAddrService{

    /**
     * 会员管理mapper
     */
    @Autowired
    private MemberAddrMapper memberAddrMapper;

    /**
     * 查询所有地址
     * @param loginMemberOpenId 登录用户openid
     * @return 地址集合
     */
    @Override
    @Cacheable(key = MemberConstant.MEMBER_ADDR_CACHE_KEY_PREFIX+"+#loginMemberOpenId")
    //@Cacheable(key = "T(com.chuanqihou.powershop.constant.MemberConstant).MEMBER_ADDR_CACHE_KEY_PREFIX + #loginMemberOpenId")
    public List<MemberAddr> findMemberAddrList(String loginMemberOpenId) {
        return memberAddrMapper.selectList(new LambdaQueryWrapper<MemberAddr>()
                .eq(MemberAddr::getOpenId, loginMemberOpenId)
                .eq(MemberAddr::getStatus, 1)
        );
    }

    /**
     * 修改默认收货地址
     * @param addrId 收货地址id
     * @param openId openid
     */
    @Override
    @CacheEvict(key = MemberConstant.MEMBER_ADDR_CACHE_KEY_PREFIX+"+#openId")
    //@Cacheable(key = "T(com.chuanqihou.powershop.constant.MemberConstant).MEMBER_ADDR_CACHE_KEY_PREFIX + #openId")
    public void modifyDefaultAddr(Long addrId,String openId) {
        // 查询登录用户之前是否设置过默认收货地址
        MemberAddr oldAddrDefault = memberAddrMapper.selectOne(new LambdaQueryWrapper<MemberAddr>()
                .eq(MemberAddr::getOpenId, openId)
                .eq(MemberAddr::getCommonAddr, 1)
        );
        if (!ObjectUtils.isEmpty(oldAddrDefault)) {
            // 如果之前设置过则取消
            oldAddrDefault.setCommonAddr(0);
            int updateOld = memberAddrMapper.updateById(oldAddrDefault);
            if (updateOld != 1) {
                throw new RuntimeException("将之前的默认地址移除失败，请联系管理员！");
            }
        }
        // 程序执行到此处，代表该登录用户下已经没有设置默认地址了
        // 设置新的默认地址
        MemberAddr newDefaultAddr = new MemberAddr();
        newDefaultAddr.setAddrId(addrId);
        newDefaultAddr.setCommonAddr(1);
        int updateNew = memberAddrMapper.updateById(newDefaultAddr);
        if (updateNew != 1) {
            throw new RuntimeException("修改默认地址失败，请联系管理员！");
        }

    }

    /**
     * 保存收货地址
     * @param memberAddr 地址信息
     * @param openId openid
     */
    @Override
    @CacheEvict(key = MemberConstant.MEMBER_ADDR_CACHE_KEY_PREFIX+"+#openId")
    //@Cacheable(key = "T(com.chuanqihou.powershop.constant.MemberConstant).MEMBER_ADDR_CACHE_KEY_PREFIX + #openId")
    public void saveMemberAddr(MemberAddr memberAddr,String openId) {
        memberAddr.setOpenId(openId);
        memberAddr.setStatus(1);
        memberAddr.setCommonAddr(0);
        memberAddr.setCreateTime(new Date());
        memberAddr.setUpdateTime(new Date());
        int insertMemberAddr = memberAddrMapper.insert(memberAddr);
        if (insertMemberAddr != 1) {
            throw new RuntimeException("新增收货地址失败，请联系管理员！");
        }
    }

    /**
     * 修改地址信息
     * @param memberAddr 地址信息
     * @param openId openid
     */
    @Override
    @CacheEvict(key = MemberConstant.MEMBER_ADDR_CACHE_KEY_PREFIX+"+#openId")
    //@Cacheable(key = "T(com.chuanqihou.powershop.constant.MemberConstant).MEMBER_ADDR_CACHE_KEY_PREFIX + #openId")
    public void modifyMemberAddr(MemberAddr memberAddr,String openId) {
        memberAddr.setUpdateTime(new Date());
        int updateAddrResult = memberAddrMapper.updateById(memberAddr);
        if (updateAddrResult != 1) {
            throw new RuntimeException("修改地址失败，请联系管理员！");
        }
    }

    /**
     * 根据id删除收货地址
     * @param addrId 地址id
     * @param openId openid
     */
    @Override
    @CacheEvict(key = MemberConstant.MEMBER_ADDR_CACHE_KEY_PREFIX+"+#openId")
    //@Cacheable(key = "T(com.chuanqihou.powershop.constant.MemberConstant).MEMBER_ADDR_CACHE_KEY_PREFIX + #openId")
    public void removeSoftMemberAddrById(Long addrId,String openId) {
        MemberAddr memberAddr = new MemberAddr();
        memberAddr.setAddrId(addrId);
        memberAddr.setStatus(0);
        int softDelAddrResult = memberAddrMapper.updateById(memberAddr);
        if (softDelAddrResult != 1) {
            throw new RuntimeException("删除收货地址失败，请联系管理员！");
        }
    }
}
