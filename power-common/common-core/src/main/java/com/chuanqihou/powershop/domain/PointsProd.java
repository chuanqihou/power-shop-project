package com.chuanqihou.powershop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@ApiModel(description = "points_prod")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "points_prod")
public class PointsProd implements Serializable {
    /**
     * 积分商品id
     */
    @TableId(value = "points_prod_id", type = IdType.AUTO)
    @ApiModelProperty(value = "积分商品id")
    @Schema(description = "积分商品id")
    private Long pointsProdId;

    /**
     * 所需积分id
     */
    @TableField(value = "points_id")
    @ApiModelProperty(value = "所需积分id")
    @Schema(description = "所需积分id")
    private Long pointsId;

    /**
     * 所需积分量
     */
    @TableField(value = "points_number")
    @ApiModelProperty(value = "所需积分量")
    @Schema(description = "所需积分量")
    private Double pointsNumber;

    /**
     * 所需金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "所需金额")
    @Schema(description = "所需金额")
    private BigDecimal amount;

    /**
     * 关联商品id
     */
    @TableField(value = "prod_id")
    @ApiModelProperty(value = "关联商品id")
    @Schema(description = "关联商品id")
    private Long prodId;

    /**
     * 库存
     */
    @TableField(value = "stocks")
    @ApiModelProperty(value = "库存")
    @Schema(description = "库存")
    private Integer stocks;

    /**
     * 状态(0下架 1上架)
     */
    @TableField(value = "`state`")
    @ApiModelProperty(value = "状态(0下架 1上架)")
    @Schema(description = "状态(0下架 1上架)")
    private Integer state;

    /**
     * 上架时间
     */
    @TableField(value = "upper_shelf_time")
    @ApiModelProperty(value = "上架时间")
    @Schema(description = "上架时间")
    private Date upperShelfTime;

    /**
     * 下架时间
     */
    @TableField(value = "lower_shelf")
    @ApiModelProperty(value = "下架时间")
    @Schema(description = "下架时间")
    private Date lowerShelf;

    private static final long serialVersionUID = 1L;
}
