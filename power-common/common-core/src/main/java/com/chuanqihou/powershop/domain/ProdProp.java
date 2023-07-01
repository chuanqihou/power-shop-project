package com.chuanqihou.powershop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@ApiModel(description = "prod_prop")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "prod_prop")
public class ProdProp implements Serializable {
    /**
     * 属性id
     */
    @TableId(value = "prop_id", type = IdType.AUTO)
    @ApiModelProperty(value = "属性id")
    @Schema(description = "属性id")
    private Long propId;

    /**
     * 属性名称
     */
    @TableField(value = "prop_name")
    @ApiModelProperty(value = "属性名称")
    @Schema(description = "属性名称")
    private String propName;

    /**
     * ProdPropRule 1:销售属性(规格); 2:参数属性;
     */
    @TableField(value = "`rule`")
    @ApiModelProperty(value = "ProdPropRule 1:销售属性(规格); 2:参数属性;")
    @Schema(description = "ProdPropRule 1:销售属性(规格); 2:参数属性;")
    private Integer rule;

    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value = "店铺id")
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 属性值
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "属性值")
    @Schema(description = "属性值")
    private List<ProdPropValue> prodPropValues;

    private static final long serialVersionUID = 1L;
}
