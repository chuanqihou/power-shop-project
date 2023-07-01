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
@ApiModel(description = "message")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "message")
public class Message implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long id;

    /**
     * 留言创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "留言创建时间")
    @Schema(description = "留言创建时间")
    private Date createTime;

    /**
     * 姓名
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value = "姓名")
    @Schema(description = "姓名")
    private String userName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    @Schema(description = "邮箱")
    private String email;

    /**
     * 联系方式
     */
    @TableField(value = "contact")
    @ApiModelProperty(value = "联系方式")
    @Schema(description = "联系方式")
    private String contact;

    /**
     * 留言内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "留言内容")
    @Schema(description = "留言内容")
    private String content;

    /**
     * 留言回复
     */
    @TableField(value = "reply")
    @ApiModelProperty(value = "留言回复")
    @Schema(description = "留言回复")
    private String reply;

    /**
     * 状态：0:未审核 1审核通过
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态：0:未审核 1审核通过")
    @Schema(description = "状态：0:未审核 1审核通过")
    private Integer status;

    private static final long serialVersionUID = 1L;
}
