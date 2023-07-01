package com.chuanqihou.powershop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */

/**
 * 系统配置信息表
 */
@ApiModel(description = "系统配置信息表")
@Schema(description = "系统配置信息表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_config")
public class SysConfig implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long id;

    /**
     * key
     */
    @TableField(value = "param_key")
    @ApiModelProperty(value = "key")
    @Schema(description = "key")
    private String paramKey;

    /**
     * value
     */
    @TableField(value = "param_value")
    @ApiModelProperty(value = "value")
    @Schema(description = "value")
    private String paramValue;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    @Schema(description = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}