package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.domain.SysMenu;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.SysMenuService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.vo.MenuAndAuthVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 16:52
 * @description 菜单控制器
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    /**
     * 菜单服务
     */
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取导航菜单
     * @return 导航菜单
     */
    @ApiOperation("获取导航菜单")
    @GetMapping("/nav")
    public Result<MenuAndAuthVo> getNav() {
        // 创建MenuAndAuthVo对象
        MenuAndAuthVo menuAndAuthVo = new MenuAndAuthVo();
        // 设置权限
        menuAndAuthVo.setAuthorities(AuthUtil.getLoginUserPermissions());
        // 获取树形菜单列表
        List<SysMenu> menuthList = sysMenuService.getMenuListByUserId(AuthUtil.getLoginUserId());
        // 设置菜单列表
        menuAndAuthVo.setMenuList(menuthList);
        // 返回结果
        return Result.success(menuAndAuthVo);
    }

}
