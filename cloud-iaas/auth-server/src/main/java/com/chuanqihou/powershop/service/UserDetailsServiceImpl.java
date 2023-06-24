package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.constant.AuthConstants;
import com.chuanqihou.powershop.domain.LoginSysUser;
import com.chuanqihou.powershop.mapper.LoginSysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author 传奇后
 * @date 2023/6/19 20:30
 * @description 用户信息服务
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户信息Mapper
     */
    @Autowired
    private LoginSysUserMapper loginSysUserMapper;

    /**
     * 根据用户名加载用户信息
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException  用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取请求头中的登录类型
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String loginType = request.getHeader(AuthConstants.LOGIN_TYPE);
        //判断登录类型是否存在
        if (!StringUtils.hasText(loginType)) {
            log.info("非法登录，登录类型不存在");
            return null;
        }
        //根据登录类型进行不同的登录操作
        switch (loginType) {
            case AuthConstants.SYS_USER:
                //后台管理登录
                log.info("后台管理登录");
                //根据用户名查询数据库
                LambdaQueryWrapper<LoginSysUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(LoginSysUser::getUsername, username);
                //查询用户信息
                LoginSysUser loginSysUser = loginSysUserMapper.selectOne(queryWrapper);
                //判断用户是否存在
                if (!ObjectUtils.isEmpty(loginSysUser)) {
                    //查询该用户所具有的权限
                    Set<String> userPerms = loginSysUserMapper.getPermsByUserId(loginSysUser.getUserId());
                    //将用户信息和权限信息封装到UserDetails对象中
                    loginSysUser.setPerms(userPerms);
                    return loginSysUser;
                }
                break;
            case AuthConstants.WX_USER:
                //微信小程序用户登录
                log.info("微信小程序用户登录");
                break;
        }
        log.info("用户不存在");
        return null;
    }
}
