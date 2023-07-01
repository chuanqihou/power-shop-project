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
@ApiModel(description = "branch_table")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "branch_table")
public class BranchTable implements Serializable {
    /**
     * 分支事务ID
     */
    @TableId(value = "branch_id", type = IdType.AUTO)
    @ApiModelProperty(value = "分支事务ID")
    @Schema(description = "分支事务ID")
    private Long branchId;

    /**
     * 全局事务ID
     */
    @TableField(value = "xid")
    @ApiModelProperty(value = "全局事务ID")
    @Schema(description = "全局事务ID")
    private String xid;

    /**
     * 全局事务ID，不带TC地址
     */
    @TableField(value = "transaction_id")
    @ApiModelProperty(value = "全局事务ID，不带TC地址")
    @Schema(description = "全局事务ID，不带TC地址")
    private Long transactionId;

    /**
     * 资源分组ID
     */
    @TableField(value = "resource_group_id")
    @ApiModelProperty(value = "资源分组ID")
    @Schema(description = "资源分组ID")
    private String resourceGroupId;

    /**
     * 资源ID
     */
    @TableField(value = "resource_id")
    @ApiModelProperty(value = "资源ID")
    @Schema(description = "资源ID")
    private String resourceId;

    /**
     * 事务模式，AT、XA等
     */
    @TableField(value = "branch_type")
    @ApiModelProperty(value = "事务模式，AT、XA等")
    @Schema(description = "事务模式，AT、XA等")
    private String branchType;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    @Schema(description = "状态")
    private Integer status;

    /**
     * 客户端ID
     */
    @TableField(value = "client_id")
    @ApiModelProperty(value = "客户端ID")
    @Schema(description = "客户端ID")
    private String clientId;

    /**
     * 应用数据
     */
    @TableField(value = "application_data")
    @ApiModelProperty(value = "应用数据")
    @Schema(description = "应用数据")
    private String applicationData;

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
