package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.vo.SysUserVO;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 系统用户业务接口
 */
public interface SysUserService extends IService<SysUser>{

    /**
     * 分页查询用户列表
     * @param page 分页对象
     * @param sysUser 查询条件
     * @return  用户列表
     */
    Page<SysUser> userListPage(Page<SysUser> page, SysUser sysUser);

    /**
     * 保存用户信息
     * @param sysUserVO 用户信息
     * @return 是否保存成功
     */
    boolean saveSysUser(SysUserVO sysUserVO);

    /**
     * 根据用户id（批量）删除用户
     * @param userIdList 用户id集合
     * @return 是否删除成功
     */
    boolean removeSysUserByIds(List<Long> userIdList);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    SysUserVO findSysUserInfoByUserId(Long userId);

    /**
     * 修改用户信息
     * @param sysUserVO 用户信息
     * @return 是否修改成功
     */
    boolean modifySysUser(SysUserVO sysUserVO);
}
