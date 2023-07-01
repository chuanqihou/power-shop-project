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
 * 商品收藏表
 */
@ApiModel(description = "商品收藏表")
@Schema(description = "商品收藏表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "prod_favorite")
public class ProdFavorite implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "favorite_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    @Schema(description = "主键")
    private Long favoriteId;

    /**
     * 商品ID
     */
    @TableField(value = "prod_id")
    @ApiModelProperty(value = "商品ID")
    @Schema(description = "商品ID")
    private Long prodId;

    /**
     * 收藏时间
     */
    @TableField(value = "rec_time")
    @ApiModelProperty(value = "收藏时间")
    @Schema(description = "收藏时间")
    private Date recTime;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    @Schema(description = "用户ID")
    private String userId;

    private static final long serialVersionUID = 1L;
}