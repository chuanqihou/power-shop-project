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
@ApiModel(description = "transfee_free")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "transfee_free")
public class TransfeeFree implements Serializable {
    /**
     * 指定条件包邮项id
     */
    @TableId(value = "transfee_free_id", type = IdType.AUTO)
    @ApiModelProperty(value = "指定条件包邮项id")
    @Schema(description = "指定条件包邮项id")
    private Long transfeeFreeId;

    /**
     * 运费模板id
     */
    @TableField(value = "transport_id")
    @ApiModelProperty(value = "运费模板id")
    @Schema(description = "运费模板id")
    private Long transportId;

    /**
     * 包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）
     */
    @TableField(value = "free_type")
    @ApiModelProperty(value = "包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）")
    @Schema(description = "包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）")
    private Integer freeType;

    /**
     * 需满金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "需满金额")
    @Schema(description = "需满金额")
    private BigDecimal amount;

    /**
     * 包邮x件/重量/体积
     */
    @TableField(value = "piece")
    @ApiModelProperty(value = "包邮x件/重量/体积")
    @Schema(description = "包邮x件/重量/体积")
    private BigDecimal piece;

    private static final long serialVersionUID = 1L;
}
