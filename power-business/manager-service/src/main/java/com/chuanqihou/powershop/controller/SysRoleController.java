package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.domain.SysRole;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/26 12:27
 * @description 角色控制器
 */
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    /**
     * 角色服务
     */
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色列表
     * @return 角色列表
     */
    @ApiOperation("查询角色列表")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/list")
    public Result<List<SysRole>> roleList() {
        // 查询角色列表
        List<SysRole> list = sysRoleService.list();
        // 返回角色列表
        return Result.success(list);
    }

}
