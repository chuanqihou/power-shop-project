package com.chuanqihou.powershop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author 传奇后
 * @date 2023/6/19 21:10
 * @description 系统用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class LoginSysUser implements Serializable, UserDetails {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 创建者ID
     */
    @TableField(value = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 用户所在的商城Id
     */
    @TableField(value = "shop_id")
    private Long shopId;

    /**
     * 用户角色权限
     */
    @TableField(exist = false)
    private Set<String> perms;

    private static final long serialVersionUID = 1L;

    /**
     * 设置用户权限
     * @param perms 权限列表
     */
    public void setPerms(Set<String> perms) {
        //将多个权限字符串拆分为Set集合
        Set<String> realPerms = new HashSet<>();
        //遍历权限列表
        perms.forEach(perm -> {
            //将权限列表中的每个权限字符串拆分为Set集合
            String[] split = perm.split(",");
            //将拆分后的权限字符串添加到Set集合中
            realPerms.addAll(Arrays.asList(split));
        });
        //将Set集合设置为用户权限
        this.perms = realPerms;
    }

    /**
     * 获取用户权限
     * @return 权限列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 判断账户是否过期
     * @return true:未过期 false:已过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return status == 1;
    }

    /**
     * 判断账户是否被锁定
     * @return true:未锁定 false:已锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }

    /**
     * 判断账户密码是否过期
     * @return true:未过期 false:已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return status == 1;
    }

    /**
     * 判断账户是否可用
     * @return true:可用 false:不可用
     */
    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}
