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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@ApiModel(description = "transfee")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "transfee")
public class Transfee implements Serializable {
    /**
     * 运费项id
     */
    @TableId(value = "transfee_id", type = IdType.AUTO)
    @ApiModelProperty(value = "运费项id")
    @Schema(description = "运费项id")
    private Long transfeeId;

    /**
     * 运费模板id
     */
    @TableField(value = "transport_id")
    @ApiModelProperty(value = "运费模板id")
    @Schema(description = "运费模板id")
    private Long transportId;

    /**
     * 续件数量
     */
    @TableField(value = "continuous_piece")
    @ApiModelProperty(value = "续件数量")
    @Schema(description = "续件数量")
    private BigDecimal continuousPiece;

    /**
     * 首件数量
     */
    @TableField(value = "first_piece")
    @ApiModelProperty(value = "首件数量")
    @Schema(description = "首件数量")
    private BigDecimal firstPiece;

    /**
     * 续件费用
     */
    @TableField(value = "continuous_fee")
    @ApiModelProperty(value = "续件费用")
    @Schema(description = "续件费用")
    private BigDecimal continuousFee;

    /**
     * 首件费用
     */
    @TableField(value = "first_fee")
    @ApiModelProperty(value = "首件费用")
    @Schema(description = "首件费用")
    private BigDecimal firstFee;

    private static final long serialVersionUID = 1L;
}