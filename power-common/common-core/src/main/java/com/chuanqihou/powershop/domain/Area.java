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
@ApiModel(description = "area")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "area")
public class Area implements Serializable {
    @TableId(value = "area_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long areaId;

    @TableField(value = "area_name")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String areaName;

    @TableField(value = "parent_id")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long parentId;

    @TableField(value = "`level`")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Integer level;

    private static final long serialVersionUID = 1L;
}