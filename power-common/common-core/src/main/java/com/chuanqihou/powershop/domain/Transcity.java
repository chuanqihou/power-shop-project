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
@ApiModel(description = "transcity")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "transcity")
public class Transcity implements Serializable {
    @TableId(value = "transcity_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long transcityId;

    /**
     * 运费项id
     */
    @TableField(value = "transfee_id")
    @ApiModelProperty(value = "运费项id")
    @Schema(description = "运费项id")
    private Long transfeeId;

    /**
     * 城市id
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "城市id")
    @Schema(description = "城市id")
    private Long cityId;

    private static final long serialVersionUID = 1L;
}