package com.chuanqihou.powershop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanqihou.powershop.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 用户角色关系表 Mapper 接口
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id查询角色id列表
     * @param userId 用户id
     * @return 角色id列表
     */
    List<Long> selectRoleIdListByUserId(Long userId);
}
