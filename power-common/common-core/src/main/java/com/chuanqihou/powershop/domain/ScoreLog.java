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
@ApiModel(description = "score_log")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "score_log")
public class ScoreLog implements Serializable {
    /**
     * 积分记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "积分记录id")
    @Schema(description = "积分记录id")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    @Schema(description = "用户id")
    private String userId;

    /**
     * 0支出 1收入
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "0支出 1收入")
    @Schema(description = "0支出 1收入")
    private Integer type;

    /**
     * 记录创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "记录创建时间")
    @Schema(description = "记录创建时间")
    private Date createTime;

    /**
     * 流水号
     */
    @TableField(value = "sn")
    @ApiModelProperty(value = "流水号")
    @Schema(description = "流水号")
    private String sn;

    /**
     * 积分类型：1回收加积分 2购买减积分
     */
    @TableField(value = "score_type")
    @ApiModelProperty(value = "积分类型：1回收加积分 2购买减积分")
    @Schema(description = "积分类型：1回收加积分 2购买减积分")
    private Integer scoreType;

    private static final long serialVersionUID = 1L;
}
