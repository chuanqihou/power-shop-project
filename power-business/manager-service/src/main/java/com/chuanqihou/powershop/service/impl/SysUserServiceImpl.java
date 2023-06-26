package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.ManagerConstant;
import com.chuanqihou.powershop.domain.SysUserRole;
import com.chuanqihou.powershop.mapper.SysUserRoleMapper;
import com.chuanqihou.powershop.service.SysUserRoleService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.util.PasswordEncoderUtil;
import com.chuanqihou.powershop.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.mapper.SysUserMapper;
import com.chuanqihou.powershop.domain.SysUser;
import com.chuanqihou.powershop.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 系统用户业务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    /**
     * 系统用户mapper
     */
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 系统用户角色service
     */
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 系统用户角色mapper
     */
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 分页查询用户列表
     * @param page 分页对象
     * @param sysUser 用户对象
     * @return 用户分页对象
     */
    @Override
    public Page<SysUser> userListPage(Page<SysUser> page, SysUser sysUser) {
        //获取查询条件
        String username = sysUser.getUsername();
        //创建分页对象
        Page<SysUser> userPage = new Page<>(page.getCurrent(),page.getSize());
        //创建查询条件
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        //设置查询条件
        queryWrapper.like(StringUtils.hasText(username),SysUser::getUsername, username);
        // 如果是管理员则查询所有店铺的用户 否则查询当前店铺的用户
        queryWrapper.eq(!ManagerConstant.ADMIN_SHOP_ID.equals(AuthUtil.getShopId()),SysUser::getShopId, AuthUtil.getShopId());
        //设置排序条件 按照创建时间降序排序
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        //执行分页查询
        sysUserMapper.selectPage(userPage, queryWrapper);
        //返回分页对象
        return userPage;
    }

    /**
     * 保存用户信息
     * @param sysUserVO 用户信息
     * @return 是否保存成功
     */
    @Override
    @Transactional
    public boolean saveSysUser(SysUserVO sysUserVO) {
        /*
            添加用户信息
         */
        //设置创建时间
        sysUserVO.setPassword(PasswordEncoderUtil.passwordEncoder(sysUserVO.getPassword()));
        //设置创建人
        sysUserVO.setCreateTime(new Date());
        //设置创建人id
        sysUserVO.setCreateUserId(AuthUtil.getLoginUserId());
        //设置店铺id
        sysUserVO.setShopId(AuthUtil.getShopId());
        // 执行添加用户操作
        int insertUserResult = sysUserMapper.insert(sysUserVO);

        /*
            添加用户角色信息
         */

        //获取用户角色id集合
        List<Long> roleIdList = sysUserVO.getRoleIdList();
        //创建用户角色对象集合
        List<SysUserRole> sysUserRoleList = roleIdList.stream().map(roleId -> {
            //创建用户角色对象
            SysUserRole sysUserRole = new SysUserRole();
            //设置角色id
            sysUserRole.setRoleId(roleId);
            //设置用户id
            sysUserRole.setUserId(sysUserVO.getUserId());
            //返回用户角色对象
            return sysUserRole;
        }).collect(Collectors.toList());
        //执行添加用户角色操作
        boolean saveUserRoleResult = sysUserRoleService.saveBatch(sysUserRoleList);

        //返回是否添加成功 true:添加成功 false:添加失败
        return saveUserRoleResult && insertUserResult==1;
    }

    /**
     * 根据用户id删除用户信息
     * @param userIdList 用户id集合
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public boolean removeSysUserByIds(List<Long> userIdList) {
        //删除用户关联的角色信息
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysUserRole::getUserId, userIdList);
        boolean delUserRoleResult = sysUserRoleService.remove(queryWrapper);
        //删除用户信息
        boolean delUserResult = this.removeBatchByIds(userIdList);
        //返回是否删除成功 true:删除成功 false:删除失败
        return delUserRoleResult && delUserResult;
    }

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public SysUserVO findSysUserInfoByUserId(Long userId) {
        SysUserVO sysUserVO = new SysUserVO();
        SysUser sysUser = sysUserMapper.selectById(userId);
        //将sysUser的属性值复制到sysUserVO中
        BeanUtils.copyProperties(sysUser, sysUserVO);
        //查询用户关联的角色id集合
        List<Long> roleIdList = sysUserRoleMapper.selectRoleIdListByUserId(sysUserVO.getUserId());
        //设置角色id集合
        sysUserVO.setRoleIdList(roleIdList);
        //返回用户信息
        return sysUserVO;
    }

    /**
     * 修改用户信息
     * @param sysUserVO 用户信息
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public boolean modifySysUser(SysUserVO sysUserVO) {
        //删除该用户的所有角色信息
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, sysUserVO.getUserId());
        // 根据用户id删除用户角色信息
        boolean removeBatchUserRoleResult = sysUserRoleService.remove(queryWrapper);
        //重新添加该用户的角色信息
        List<Long> roleIdList = sysUserVO.getRoleIdList();
        List<SysUserRole> sysUserRoleList = roleIdList.stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(sysUserVO.getUserId());
            return sysUserRole;
        }).collect(Collectors.toList());
        //添加用户角色信息
        boolean saveUserRoleResult = sysUserRoleService.saveBatch(sysUserRoleList);
        //修改用户其他信息
        int updateSysUserResult = sysUserMapper.updateById(sysUserVO);
        //返回是否修改成功 true:修改成功 false:修改失败
        return removeBatchUserRoleResult && saveUserRoleResult && updateSysUserResult==1;
    }
}
