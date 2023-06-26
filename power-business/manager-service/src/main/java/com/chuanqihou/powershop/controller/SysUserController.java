package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.annotation.LogMethod;
import com.chuanqihou.powershop.domain.LoginSysUser;
import com.chuanqihou.powershop.domain.SysUser;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.SysUserService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 传奇后
 * @date 2023/6/25 18:38
 * @description 用户控制器
 */
@Api(tags = "系统管理员接口")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    /**
     * 用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前登录用户信息
     * @return 当前用户信息
     */
    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/info")
    public Result<LoginSysUser> getCurrentLoginUserInfo() {
        // 返回当前登录用户信息
        return Result.success(AuthUtil.getLoginUser());
    }

    /**
     * 分页查询获取用户列表
     * @param page 分页参数
     * @param sysUser 查询条件
     * @return 用户列表
     */
    @ApiOperation("分页查询获取用户列表")
    @PreAuthorize("hasAuthority('sys:user:page')")
    @GetMapping("/page")
    public Result<Page<SysUser>> page(Page<SysUser> page,SysUser sysUser) {
        // 分页查询用户列表
        Page<SysUser> sysUserPage = sysUserService.userListPage(page,sysUser);
        // 返回用户列表
        return Result.success(sysUserPage);
    }

    /**
     * 新增系统管理员
     * @param sysUserVO 系统管理员信息
     * @return 新增结果
     */
    @ApiOperation("新增系统管理员")
    @LogMethod("新增系统管理员")
    @PreAuthorize("hasAuthority('sys:user:save')")
    @PostMapping
    public Result addSysUser(@RequestBody SysUserVO sysUserVO) {
        // 新增系统管理员
        boolean result = sysUserService.saveSysUser(sysUserVO);
        // 返回新增结果 result 为 true 返回成功，否则返回失败
        return result ? Result.success() : Result.fails(-1, "添加系统管理员失败！");
    }

    /**
     * 删除系统管理员
     * @param userIdsStr 用户id字符串
     * @return 删除结果
     */
    @ApiOperation("删除系统管理员")
    @LogMethod("删除系统管理员")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/{userIdStr}")
    public Result<Object> cutSysUserByUserIds(@PathVariable("userIdStr") String userIdsStr) {
        // 将字符串转换为数组
        String[] split = userIdsStr.split(",");
        // 将数组转换为集合并转换为Long类型
        List<Long> userIdList = Arrays.stream(split)
                .map(Long::valueOf)
                .collect(Collectors.toList());
        // 删除系统管理员
        boolean result = sysUserService.removeSysUserByIds(userIdList);
        // 返回删除结果 result 为 true 返回成功，否则返回失败
        return result ? Result.success() : Result.fails(-1, "删除失败！");
    }

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @ApiOperation("根据用户id获取用户信息")
    @PreAuthorize("hasAuthority('sys:user:info')")
    @GetMapping("/info/{userId}")
    public Result<SysUserVO> getSysUserInfoById(@PathVariable("userId") Long userId) {
        // 根据用户id获取用户信息
        SysUserVO sysUserVO = sysUserService.findSysUserInfoByUserId(userId);
        // 返回用户信息
        return Result.success(sysUserVO);
    }

    /**
     * 修改系统管理员信息
     * @param sysUserVO 系统管理员信息
     * @return 修改结果
     */
    @ApiOperation("修改系统管理员信息")
    @LogMethod("修改系统管理员信息")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PutMapping
    public Result<Object> editSysUser(@RequestBody SysUserVO sysUserVO) {
        // 修改系统管理员
        boolean editResult = sysUserService.modifySysUser(sysUserVO);
        // 返回修改结果 editResult 为 true 返回成功，否则返回失败
        return editResult ? Result.success() : Result.fails(-1, "修改失败！");
    }

}
