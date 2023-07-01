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
@ApiModel(description = "transcity_free")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "transcity_free")
public class TranscityFree implements Serializable {
    /**
     * 指定条件包邮城市项id
     */
    @TableId(value = "transcity_free_id", type = IdType.AUTO)
    @ApiModelProperty(value = "指定条件包邮城市项id")
    @Schema(description = "指定条件包邮城市项id")
    private Long transcityFreeId;

    /**
     * 指定条件包邮项id
     */
    @TableField(value = "transfee_free_id")
    @ApiModelProperty(value = "指定条件包邮项id")
    @Schema(description = "指定条件包邮项id")
    private Long transfeeFreeId;

    /**
     * 城市id
     */
    @TableField(value = "free_city_id")
    @ApiModelProperty(value = "城市id")
    @Schema(description = "城市id")
    private Long freeCityId;

    private static final long serialVersionUID = 1L;
}