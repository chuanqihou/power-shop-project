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
@ApiModel(description = "order_settlement")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "order_settlement")
public class OrderSettlement implements Serializable {
    /**
     * 支付结算单据ID
     */
    @TableId(value = "settlement_id", type = IdType.AUTO)
    @ApiModelProperty(value = "支付结算单据ID")
    @Schema(description = "支付结算单据ID")
    private Long settlementId;

    /**
     * 外部订单流水号
     */
    @TableField(value = "biz_pay_no")
    @ApiModelProperty(value = "外部订单流水号")
    @Schema(description = "外部订单流水号")
    private String bizPayNo;

    /**
     * order表中的订单号
     */
    @TableField(value = "order_number")
    @ApiModelProperty(value = "order表中的订单号")
    @Schema(description = "order表中的订单号")
    private String orderNumber;

    /**
     * 支付方式 1 微信支付 2 支付宝
     */
    @TableField(value = "pay_type")
    @ApiModelProperty(value = "支付方式 1 微信支付 2 支付宝")
    @Schema(description = "支付方式 1 微信支付 2 支付宝")
    private Integer payType;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    @ApiModelProperty(value = "支付金额")
    @Schema(description = "支付金额")
    private BigDecimal payAmount;

    /**
     * 是否清算 0:否 1:是
     */
    @TableField(value = "is_clearing")
    @ApiModelProperty(value = "是否清算 0:否 1:是")
    @Schema(description = "是否清算 0:否 1:是")
    private Integer isClearing;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 清算时间
     */
    @TableField(value = "clearing_time")
    @ApiModelProperty(value = "清算时间")
    @Schema(description = "清算时间")
    private Date clearingTime;

    /**
     * 版本号
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "版本号")
    @Schema(description = "版本号")
    private Integer version;

    /**
     * 支付状态
     */
    @TableField(value = "pay_status")
    @ApiModelProperty(value = "支付状态")
    @Schema(description = "支付状态")
    private Integer payStatus;

    private static final long serialVersionUID = 1L;
}