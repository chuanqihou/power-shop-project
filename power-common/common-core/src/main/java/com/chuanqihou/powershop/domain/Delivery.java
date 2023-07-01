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

/**
 * 物流公司
 */
@ApiModel(description = "物流公司")
@Schema(description = "物流公司")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "delivery")
public class Delivery implements Serializable {
    /**
     * ID
     */
    @TableId(value = "dvy_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    @Schema(description = "ID")
    private Long dvyId;

    /**
     * 物流公司名称
     */
    @TableField(value = "dvy_name")
    @ApiModelProperty(value = "物流公司名称")
    @Schema(description = "物流公司名称")
    private String dvyName;

    /**
     * 公司主页
     */
    @TableField(value = "company_home_url")
    @ApiModelProperty(value = "公司主页")
    @Schema(description = "公司主页")
    private String companyHomeUrl;

    /**
     * 建立时间
     */
    @TableField(value = "rec_time")
    @ApiModelProperty(value = "建立时间")
    @Schema(description = "建立时间")
    private Date recTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    @ApiModelProperty(value = "修改时间")
    @Schema(description = "修改时间")
    private Date modifyTime;

    /**
     * 物流查询接口
     */
    @TableField(value = "query_url")
    @ApiModelProperty(value = "物流查询接口")
    @Schema(description = "物流查询接口")
    private String queryUrl;

    private static final long serialVersionUID = 1L;
}