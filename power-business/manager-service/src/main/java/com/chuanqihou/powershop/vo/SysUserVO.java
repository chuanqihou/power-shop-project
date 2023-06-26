package com.chuanqihou.powershop.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.chuanqihou.powershop.domain.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/26 14:51
 * @description 系统用户视图对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVO extends SysUser {

    /**
     * 用户角色id集合
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户角色id")
    private List<Long> roleIdList;

}
