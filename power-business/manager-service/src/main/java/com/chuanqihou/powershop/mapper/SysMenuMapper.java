package com.chuanqihou.powershop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanqihou.powershop.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 菜单Mapper
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id获取菜单列表
     * @param loginUserId 用户id
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByUserId(Long loginUserId);
}
