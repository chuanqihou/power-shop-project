package com.chuanqihou.powershop.vo;

import com.chuanqihou.powershop.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "菜单和权限视图对象")
public class MenuAndAuthVo {

    /**
     * 权限集合
     */
    @ApiModelProperty(value = "权限集合")
    private Set<String> authorities;

    /**
     * 菜单集合
     */
    @ApiModelProperty(value = "菜单集合")
    private List<SysMenu> menuList;

}
