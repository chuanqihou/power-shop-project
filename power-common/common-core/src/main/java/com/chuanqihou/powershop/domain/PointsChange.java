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
@ApiModel(description = "points_change")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "points_change")
public class PointsChange implements Serializable {
    /**
     * 积分流动记录表
     */
    @TableId(value = "points_change_id", type = IdType.AUTO)
    @ApiModelProperty(value = "积分流动记录表")
    @Schema(description = "积分流动记录表")
    private Long pointsChangeId;

    /**
     * 积分钱包id
     */
    @TableField(value = "points_wallet_id")
    @ApiModelProperty(value = "积分钱包id")
    @Schema(description = "积分钱包id")
    private Long pointsWalletId;

    /**
     * 增加或减少(增加 0 减少 1)
     */
    @TableField(value = "add_or_reduce")
    @ApiModelProperty(value = "增加或减少(增加 0 减少 1)")
    @Schema(description = "增加或减少(增加 0 减少 1)")
    private Integer addOrReduce;

    /**
     * 原因(订单，邀请，签到，兑换)
     */
    @TableField(value = "reason")
    @ApiModelProperty(value = "原因(订单，邀请，签到，兑换)")
    @Schema(description = "原因(订单，邀请，签到，兑换)")
    private Integer reason;

    /**
     * 积分状态（0:用户未收货待结算，1:已结算 2:用户退货退单）
     */
    @TableField(value = "`state`")
    @ApiModelProperty(value = "积分状态（0:用户未收货待结算，1:已结算 2:用户退货退单）")
    @Schema(description = "积分状态（0:用户未收货待结算，1:已结算 2:用户退货退单）")
    private Integer state;

    /**
     * 积分数额
     */
    @TableField(value = "points_number")
    @ApiModelProperty(value = "积分数额")
    @Schema(description = "积分数额")
    private Double pointsNumber;

    /**
     * 关联订单id
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "关联订单id")
    @Schema(description = "关联订单id")
    private Long orderId;

    /**
     * 商户订单id
     */
    @TableField(value = "merchant_order_id")
    @ApiModelProperty(value = "商户订单id")
    @Schema(description = "商户订单id")
    private Long merchantOrderId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    @Schema(description = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
