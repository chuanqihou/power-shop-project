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
@ApiModel(description = "points_wallet")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "points_wallet")
public class PointsWallet implements Serializable {
    /**
     * 积分钱包id
     */
    @TableId(value = "points_wallet_id", type = IdType.AUTO)
    @ApiModelProperty(value = "积分钱包id")
    @Schema(description = "积分钱包id")
    private Long pointsWalletId;

    /**
     * 积分Id
     */
    @TableField(value = "points_id")
    @ApiModelProperty(value = "积分Id")
    @Schema(description = "积分Id")
    private Long pointsId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 待结算积分
     */
    @TableField(value = "unsettled")
    @ApiModelProperty(value = "待结算积分")
    @Schema(description = "待结算积分")
    private Double unsettled;

    /**
     * 已结算积分
     */
    @TableField(value = "settled")
    @ApiModelProperty(value = "已结算积分")
    @Schema(description = "已结算积分")
    private Double settled;

    /**
     * 积累收益积分
     */
    @TableField(value = "addup")
    @ApiModelProperty(value = "积累收益积分")
    @Schema(description = "积累收益积分")
    private Double addup;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    @Schema(description = "乐观锁")
    private Integer version;

    private static final long serialVersionUID = 1L;
}