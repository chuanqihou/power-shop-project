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
 * 主页轮播图
 */
@ApiModel(description = "主页轮播图")
@Schema(description = "主页轮播图")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "index_img")
public class IndexImg implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "img_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    @Schema(description = "主键")
    private Long imgId;

    /**
     * 店铺ID
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value = "店铺ID")
    @Schema(description = "店铺ID")
    private Long shopId;

    /**
     * 图片
     */
    @TableField(value = "img_url")
    @ApiModelProperty(value = "图片")
    @Schema(description = "图片")
    private String imgUrl;

    /**
     * 说明文字,描述
     */
    @TableField(value = "des")
    @ApiModelProperty(value = "说明文字,描述")
    @Schema(description = "说明文字,描述")
    private String des;

    /**
     * 标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "标题")
    @Schema(description = "标题")
    private String title;

    /**
     * 链接
     */
    @TableField(value = "link")
    @ApiModelProperty(value = "链接")
    @Schema(description = "链接")
    private String link;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    @Schema(description = "状态")
    private Boolean status;

    /**
     * 顺序
     */
    @TableField(value = "seq")
    @ApiModelProperty(value = "顺序")
    @Schema(description = "顺序")
    private Integer seq;

    /**
     * 上传时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "上传时间")
    @Schema(description = "上传时间")
    private Date createTime;

    /**
     * 关联
     */
    @TableField(value = "prod_id")
    @ApiModelProperty(value = "关联")
    @Schema(description = "关联")
    private Long prodId;

    private static final long serialVersionUID = 1L;
}