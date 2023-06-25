package com.chuanqihou.powershop.vo;

import com.chuanqihou.powershop.domain.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author 传奇后
 * @date 2023/6/25 16:57
 * @description 菜单和权限视图对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuAndAuthVo {

    /**
     * 权限集合
     */
    private Set<String> authorities;

    /**
     * 菜单集合
     */
    private List<SysMenu> menuList;

}
