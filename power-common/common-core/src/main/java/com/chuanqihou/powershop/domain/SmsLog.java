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
 * 短信记录表
 */
@ApiModel(description = "短信记录表")
@Schema(description = "短信记录表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sms_log")
public class SmsLog implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    @Schema(description = "ID")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "open_id")
    @ApiModelProperty(value = "用户id")
    @Schema(description = "用户id")
    private String openId;

    /**
     * 手机号码
     */
    @TableField(value = "user_phone")
    @ApiModelProperty(value = "手机号码")
    @Schema(description = "手机号码")
    private String userPhone;

    /**
     * 短信模板
     */
    @TableField(value = "template_id")
    @ApiModelProperty(value = "短信模板")
    @Schema(description = "短信模板")
    private String templateId;

    /**
     * 手机验证码
     */
    @TableField(value = "mobile_code")
    @ApiModelProperty(value = "手机验证码")
    @Schema(description = "手机验证码")
    private String mobileCode;

    /**
     * 发送时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "发送时间")
    @Schema(description = "发送时间")
    private Date createTime;

    /**
     * 发送短信返回码
     */
    @TableField(value = "response_code")
    @ApiModelProperty(value = "发送短信返回码")
    @Schema(description = "发送短信返回码")
    private String responseCode;

    /**
     * 状态  1:有效  0：失效
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态  1:有效  0：失效")
    @Schema(description = "状态  1:有效  0：失效")
    private Integer status;

    private static final long serialVersionUID = 1L;
}