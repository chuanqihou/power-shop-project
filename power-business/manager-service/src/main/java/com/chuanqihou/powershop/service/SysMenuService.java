package com.chuanqihou.powershop.service;

import com.chuanqihou.powershop.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 菜单服务接口
 */
public interface SysMenuService extends IService<SysMenu>{

    /**
     * 根据用户id获取树形菜单列表
     * @param loginUserId 用户id
     * @return 树形菜单列表
     */
    List<SysMenu> getMenuListByUserId(Long loginUserId);
}
