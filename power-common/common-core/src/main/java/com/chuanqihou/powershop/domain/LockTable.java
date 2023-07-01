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
@ApiModel(description = "lock_table")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "lock_table")
public class LockTable implements Serializable {
    /**
     * 行键
     */
    @TableId(value = "row_key", type = IdType.AUTO)
    @ApiModelProperty(value = "行键")
    @Schema(description = "行键")
    private String rowKey;

    /**
     * 全局事务ID
     */
    @TableField(value = "xid")
    @ApiModelProperty(value = "全局事务ID")
    @Schema(description = "全局事务ID")
    private String xid;

    /**
     * 全局事务ID，不带TC 地址
     */
    @TableField(value = "transaction_id")
    @ApiModelProperty(value = "全局事务ID，不带TC 地址")
    @Schema(description = "全局事务ID，不带TC 地址")
    private Long transactionId;

    /**
     * 分支ID
     */
    @TableField(value = "branch_id")
    @ApiModelProperty(value = "分支ID")
    @Schema(description = "分支ID")
    private Long branchId;

    /**
     * 资源ID
     */
    @TableField(value = "resource_id")
    @ApiModelProperty(value = "资源ID")
    @Schema(description = "资源ID")
    private String resourceId;

    /**
     * 表名
     */
    @TableField(value = "`table_name`")
    @ApiModelProperty(value = "表名")
    @Schema(description = "表名")
    private String tableName;

    /**
     * 主键对应的值
     */
    @TableField(value = "pk")
    @ApiModelProperty(value = "主键对应的值")
    @Schema(description = "主键对应的值")
    private String pk;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    @ApiModelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    @ApiModelProperty(value = "修改时间")
    @Schema(description = "修改时间")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}