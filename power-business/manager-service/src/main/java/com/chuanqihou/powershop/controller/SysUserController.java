package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/6/25 18:38
 * @description 用户控制器
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    /**
     * 获取当前登录用户信息
     * @return 当前用户信息
     */
    @GetMapping("/info")
    public Result getCurrentLoginUserInfo() {
        // 返回当前登录用户信息
        return Result.success(AuthUtil.getLoginUser());
    }

}
