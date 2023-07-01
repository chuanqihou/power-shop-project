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
@ApiModel(description = "transport")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "transport")
public class Transport implements Serializable {
    /**
     * 运费模板id
     */
    @TableId(value = "transport_id", type = IdType.AUTO)
    @ApiModelProperty(value = "运费模板id")
    @Schema(description = "运费模板id")
    private Long transportId;

    /**
     * 运费模板名称
     */
    @TableField(value = "trans_name")
    @ApiModelProperty(value = "运费模板名称")
    @Schema(description = "运费模板名称")
    private String transName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value = "店铺id")
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 收费方式（0 按件数,1 按重量 2 按体积）
     */
    @TableField(value = "charge_type")
    @ApiModelProperty(value = "收费方式（0 按件数,1 按重量 2 按体积）")
    @Schema(description = "收费方式（0 按件数,1 按重量 2 按体积）")
    private Integer chargeType;

    /**
     * 是否包邮 0:不包邮 1:包邮
     */
    @TableField(value = "is_free_fee")
    @ApiModelProperty(value = "是否包邮 0:不包邮 1:包邮")
    @Schema(description = "是否包邮 0:不包邮 1:包邮")
    private Integer isFreeFee;

    /**
     * 是否含有包邮条件 0 否 1是
     */
    @TableField(value = "has_free_condition")
    @ApiModelProperty(value = "是否含有包邮条件 0 否 1是")
    @Schema(description = "是否含有包邮条件 0 否 1是")
    private Integer hasFreeCondition;

    private static final long serialVersionUID = 1L;
}
