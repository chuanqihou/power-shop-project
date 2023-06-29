package com.chuanqihou.powershop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.config.WeChatSmallAppProperties;
import com.chuanqihou.powershop.constant.AuthConstants;
import com.chuanqihou.powershop.domain.LoginMember;
import com.chuanqihou.powershop.domain.LoginSysUser;
import com.chuanqihou.powershop.mapper.LoginMemberMapper;
import com.chuanqihou.powershop.mapper.LoginSysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
     * 管理员用户信息Mapper
     */
    @Autowired
    private LoginSysUserMapper loginSysUserMapper;

    /**
     * 微信小程序用户信息mapper
     */
    @Autowired
    private LoginMemberMapper loginMemberMapper;

    /**
     * 发送http请求模板对象
     */
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeChatSmallAppProperties weChatProperties;

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
        if (ObjectUtils.isEmpty(requestAttributes)) {
            throw new RuntimeException("未知错误，请联系管理员！");
        }
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
                // 获取 url
                String url = String.format(weChatProperties.getJsCode2SessionUrl(),weChatProperties.getAppId(),weChatProperties.getAppSecret(),username);
                // 发送http请求到微信API
                String forObjectJson = restTemplate.getForObject(url, String.class);
                // 获取响应数据
                JSONObject jsonObjectMap = JSON.parseObject(forObjectJson);
                // 从响应数据中获取用户的openid
                String openid = jsonObjectMap.getString("openid");
                // 判断是否拿到 openid
                if (!StringUtils.hasText(openid)) {
                    throw new RuntimeException("系统维护中，请联系管理员");
                }
                // 根据openid 查询该用户是否注册过
                LoginMember loginMember = loginMemberMapper.selectOne(new LambdaQueryWrapper<LoginMember>()
                        .eq(LoginMember::getOpenId, openid)
                );
                // 如果是没注册过，则注册（插入一条数据）
                if (ObjectUtils.isEmpty(loginMember)) {
                    loginMember = new LoginMember();
                    // 设置用户的 openid
                    loginMember.setOpenId(openid);
                    // 设置时间
                    loginMember.setCreateTime(new Date());
                    loginMember.setUpdateTime(new Date());
                    // 设置注册ip地址
                    loginMember.setUserRegip(request.getRemoteAddr());
                    // 上次登录时间
                    loginMember.setUserLasttime(new Date());
                    // 上次登录ip地址
                    loginMember.setUserLastip(request.getRemoteAddr());
                    loginMember.setStatus(1);
                    // 执行sql
                    loginMemberMapper.insert(loginMember);
                }else{
                    // 如果注册过则更新时间和ip地址
                    loginMember.setUserLasttime(new Date());
                    loginMember.setUserLastip(request.getRemoteAddr());
                    // 执行更新操作
                    loginMemberMapper.updateById(loginMember);
                }
                // 返回信息
                return loginMember;
        }
        log.info("用户不存在");
        return null;
    }
}
