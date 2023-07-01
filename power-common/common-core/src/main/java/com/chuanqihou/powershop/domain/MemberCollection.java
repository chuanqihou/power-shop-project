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
@ApiModel(description = "member_collection")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "member_collection")
public class MemberCollection implements Serializable {
    /**
     * 收藏表
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "收藏表")
    @Schema(description = "收藏表")
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "prod_id")
    @ApiModelProperty(value = "商品id")
    @Schema(description = "商品id")
    private Long prodId;

    /**
     * 用户id
     */
    @TableField(value = "open_id")
    @ApiModelProperty(value = "用户id")
    @Schema(description = "用户id")
    private String openId;

    /**
     * 收藏时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "收藏时间")
    @Schema(description = "收藏时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}