package com.chuanqihou.powershop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */

/**
 * 品牌表
 */
@ApiModel(description = "品牌表")
@Schema(description = "品牌表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "brand")
public class Brand implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "brand_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    @Schema(description = "主键")
    private Long brandId;

    /**
     * 品牌名称
     */
    @TableField(value = "brand_name")
    @ApiModelProperty(value = "品牌名称")
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 图片路径
     */
    @TableField(value = "brand_pic")
    @ApiModelProperty(value = "图片路径")
    @Schema(description = "图片路径")
    private String brandPic;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 备注
     */
    @TableField(value = "memo")
    @ApiModelProperty(value = "备注")
    @Schema(description = "备注")
    private String memo;

    /**
     * 顺序
     */
    @TableField(value = "seq")
    @ApiModelProperty(value = "顺序")
    @Schema(description = "顺序")
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "默认是1，表示正常状态,0为下线状态")
    @Schema(description = "默认是1，表示正常状态,0为下线状态")
    private Integer status;

    /**
     * 简要描述
     */
    @TableField(value = "brief")
    @ApiModelProperty(value = "简要描述")
    @Schema(description = "简要描述")
    private String brief;

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "内容")
    @Schema(description = "内容")
    private String content;

    /**
     * 记录时间
     */
    @TableField(value = "rec_time")
    @ApiModelProperty(value = "记录时间")
    @Schema(description = "记录时间")
    private Date recTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 品牌首字母
     */
    @TableField(value = "first_char")
    @ApiModelProperty(value = "品牌首字母")
    @Schema(description = "品牌首字母")
    private String firstChar;

    private static final long serialVersionUID = 1L;
}