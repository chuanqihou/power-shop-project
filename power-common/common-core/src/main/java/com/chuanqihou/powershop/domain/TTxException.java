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
@ApiModel(description = "t_tx_exception")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_tx_exception")
public class TTxException implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long id;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Date createTime;

    @TableField(value = "ex_state")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Short exState;

    @TableField(value = "group_id")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String groupId;

    @TableField(value = "mod_id")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String modId;

    @TableField(value = "registrar")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Short registrar;

    @TableField(value = "remark")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String remark;

    @TableField(value = "transaction_state")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Integer transactionState;

    @TableField(value = "unit_id")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String unitId;

    private static final long serialVersionUID = 1L;
}