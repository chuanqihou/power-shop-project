package com.chuanqihou.powershop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanqihou.powershop.domain.LoginMember;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 微信小程序用户mapper接口
 */
@Mapper
public interface LoginMemberMapper extends BaseMapper<LoginMember> {
}
