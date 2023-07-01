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
 * 定时任务
 */
@ApiModel(description = "定时任务")
@Schema(description = "定时任务")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "schedule_job")
public class ScheduleJob implements Serializable {
    /**
     * 任务id
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    @ApiModelProperty(value = "任务id")
    @Schema(description = "任务id")
    private Long jobId;

    /**
     * spring bean名称
     */
    @TableField(value = "bean_name")
    @ApiModelProperty(value = "spring bean名称")
    @Schema(description = "spring bean名称")
    private String beanName;

    /**
     * 方法名
     */
    @TableField(value = "method_name")
    @ApiModelProperty(value = "方法名")
    @Schema(description = "方法名")
    private String methodName;

    /**
     * 参数
     */
    @TableField(value = "params")
    @ApiModelProperty(value = "参数")
    @Schema(description = "参数")
    private String params;

    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    @ApiModelProperty(value = "cron表达式")
    @Schema(description = "cron表达式")
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "任务状态  0：正常  1：暂停")
    @Schema(description = "任务状态  0：正常  1：暂停")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
