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
@ApiModel(description = "category_brand")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "category_brand")
public class CategoryBrand implements Serializable {
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
     * 品牌id
     */
    @TableField(value = "brand_id")
    @ApiModelProperty(value = "品牌id")
    @Schema(description = "品牌id")
    private Long brandId;

    private static final long serialVersionUID = 1L;
}