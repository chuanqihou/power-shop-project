package com.chuanqihou.powershop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanqihou.powershop.domain.LoginSysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;


/**
 * @author 传奇后
 * @date 2023/6/19 21:10
 * @description 系统用户
 */
@Mapper
public interface LoginSysUserMapper extends BaseMapper<LoginSysUser> {

    /**
     * 根据用户id查询用户权限
     * @param userId 用户id
     * @return 用户权限
     */
    Set<String> getPermsByUserId(Long userId);
}
