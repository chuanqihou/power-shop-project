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
@ApiModel(description = "category_prop")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "category_prop")
public class CategoryProp implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long id;

    /**
     * 分类id
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "分类id")
    @Schema(description = "分类id")
    private Long categoryId;

    /**
     * 商品属性id即表prod_prop中的prop_id
     */
    @TableField(value = "prop_id")
    @ApiModelProperty(value = "商品属性id即表prod_prop中的prop_id")
    @Schema(description = "商品属性id即表prod_prop中的prop_id")
    private Long propId;

    private static final long serialVersionUID = 1L;
}