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

/**
 * 用户配送地址
 */
@ApiModel(description = "用户配送地址")
@Schema(description = "用户配送地址")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pick_addr")
public class PickAddr implements Serializable {
    /**
     * ID
     */
    @TableId(value = "addr_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    @Schema(description = "ID")
    private Long addrId;

    /**
     * 自提点名称
     */
    @TableField(value = "addr_name")
    @ApiModelProperty(value = "自提点名称")
    @Schema(description = "自提点名称")
    private String addrName;

    /**
     * 地址
     */
    @TableField(value = "addr")
    @ApiModelProperty(value = "地址")
    @Schema(description = "地址")
    private String addr;

    /**
     * 手机
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value = "手机")
    @Schema(description = "手机")
    private String mobile;

    /**
     * 省份ID
     */
    @TableField(value = "province_id")
    @ApiModelProperty(value = "省份ID")
    @Schema(description = "省份ID")
    private Long provinceId;

    /**
     * 省份
     */
    @TableField(value = "province")
    @ApiModelProperty(value = "省份")
    @Schema(description = "省份")
    private String province;

    /**
     * 城市ID
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "城市ID")
    @Schema(description = "城市ID")
    private Long cityId;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value = "城市")
    @Schema(description = "城市")
    private String city;

    /**
     * 区/县ID
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "区/县ID")
    @Schema(description = "区/县ID")
    private Long areaId;

    /**
     * 区/县
     */
    @TableField(value = "area")
    @ApiModelProperty(value = "区/县")
    @Schema(description = "区/县")
    private String area;

    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value = "店铺id")
    @Schema(description = "店铺id")
    private Long shopId;

    private static final long serialVersionUID = 1L;
}