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
 * 定时任务日志
 */
@ApiModel(description = "定时任务日志")
@Schema(description = "定时任务日志")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "schedule_job_log")
public class ScheduleJobLog implements Serializable {
    /**
     * 任务日志id
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "任务日志id")
    @Schema(description = "任务日志id")
    private Long logId;

    /**
     * 任务id
     */
    @TableField(value = "job_id")
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
     * 任务状态    0：成功    1：失败
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "任务状态    0：成功    1：失败")
    @Schema(description = "任务状态    0：成功    1：失败")
    private Integer status;

    /**
     * 失败信息
     */
    @TableField(value = "error")
    @ApiModelProperty(value = "失败信息")
    @Schema(description = "失败信息")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @TableField(value = "times")
    @ApiModelProperty(value = "耗时(单位：毫秒)")
    @Schema(description = "耗时(单位：毫秒)")
    private Integer times;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
