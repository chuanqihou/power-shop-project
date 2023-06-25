package com.chuanqihou.powershop.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.SysMenu;
import com.chuanqihou.powershop.mapper.SysMenuMapper;
import com.chuanqihou.powershop.service.SysMenuService;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 菜单服务实现类
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    /**
     * 菜单Mapper
     */
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据用户id获取树形菜单列表
     * @param loginUserId 用户id
     * @return 树形菜单列表
     */
    @Override
    public List<SysMenu> getMenuListByUserId(Long loginUserId) {
/*        // 获取菜单列表
        List<SysMenu> sysMenus = sysMenuMapper.selectMenuListByUserId(loginUserId);*/

        // 获取菜单列表（从redis中获取）
        String menuListJson = redisTemplate.opsForValue().get("menuList");
        List<SysMenu> sysMenus = null;
        if (StringUtils.hasText(menuListJson)) {
            sysMenus = JSON.parseArray(menuListJson, SysMenu.class);
        }else{
            // 从数据库中获取菜单列表 并存入redis
            sysMenus = sysMenuMapper.selectMenuListByUserId(loginUserId);
            redisTemplate.opsForValue().set("menuList", JSON.toJSONString(sysMenus),30, TimeUnit.DAYS);
        }
        // 转换为树形菜单列表，并返回
        return getTreeMenuListRecursion(sysMenus,0L);
    }

    /**
     * 递归获取树形菜单列表
     * @param sysMenus 所有菜单列表
     * @param parentId 父菜单id
     * @return 树形菜单列表
     */
    private List<SysMenu> getTreeMenuListRecursion(List<SysMenu> sysMenus, long parentId) {
        // 获取父菜单列表
        List<SysMenu> parentMenuList = sysMenus.stream().filter(sysMenu -> sysMenu.getParentId().equals(parentId)).collect(Collectors.toList());
        // 遍历父菜单列表
        parentMenuList.forEach(sysMenu -> {
            // 递归获取子菜单列表 并设置到父菜单中
            sysMenu.setList(getTreeMenuListRecursion(sysMenus,sysMenu.getMenuId()));
        });
        // 返回父菜单列表
        return parentMenuList;
    }

    /**
     * 获取树形菜单列表（两级） 普通方法
     * @param sysMenus 所有菜单列表
     * @param parentId 父菜单id
     * @return 树形菜单列表
     */
    private List<SysMenu> getTreeMenuList(List<SysMenu> sysMenus, long parentId) {
        List<SysMenu> parentMenuList = sysMenus.stream().filter(sysMenu -> sysMenu.getParentId().equals(parentId)).collect(Collectors.toList());
        parentMenuList.forEach(sysMenu -> {
            List<SysMenu> collect = sysMenus.stream().filter(s -> s.getParentId().equals(sysMenu.getMenuId())).collect(Collectors.toList());
            sysMenu.setList(collect);
        });
        return parentMenuList;
    }

}
