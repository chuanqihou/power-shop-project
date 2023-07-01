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
import lombok.experimental.Accessors;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@ApiModel(description = "prod_tag_reference")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "prod_tag_reference")
public class ProdTagReference implements Serializable {
    /**
     * 分组引用id
     */
    @TableId(value = "reference_id", type = IdType.AUTO)
    @ApiModelProperty(value = "分组引用id")
    @Schema(description = "分组引用id")
    private Long referenceId;

    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value = "店铺id")
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 标签id
     */
    @TableField(value = "tag_id")
    @ApiModelProperty(value = "标签id")
    @Schema(description = "标签id")
    private Long tagId;

    /**
     * 商品id
     */
    @TableField(value = "prod_id")
    @ApiModelProperty(value = "商品id")
    @Schema(description = "商品id")
    private Long prodId;

    /**
     * 状态(1:正常,0:删除)
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态(1:正常,0:删除)")
    @Schema(description = "状态(1:正常,0:删除)")
    private Boolean status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
