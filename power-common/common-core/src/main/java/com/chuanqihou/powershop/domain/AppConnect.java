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
@ApiModel(description = "app_connect")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "app_connect")
public class AppConnect implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    @Schema(description = "id")
    private Long id;

    /**
     * 本系统userId
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "本系统userId")
    @Schema(description = "本系统userId")
    private String userId;

    /**
     * 第三方系统id 1：微信小程序
     */
    @TableField(value = "app_id")
    @ApiModelProperty(value = "第三方系统id 1：微信小程序")
    @Schema(description = "第三方系统id 1：微信小程序")
    private Integer appId;

    /**
     * 第三方系统昵称
     */
    @TableField(value = "nick_name")
    @ApiModelProperty(value = "第三方系统昵称")
    @Schema(description = "第三方系统昵称")
    private String nickName;

    /**
     * 第三方系统头像
     */
    @TableField(value = "image_url")
    @ApiModelProperty(value = "第三方系统头像")
    @Schema(description = "第三方系统头像")
    private String imageUrl;

    /**
     * 第三方系统userid
     */
    @TableField(value = "biz_user_id")
    @ApiModelProperty(value = "第三方系统userid")
    @Schema(description = "第三方系统userid")
    private String bizUserId;

    /**
     * 第三方系统unionid
     */
    @TableField(value = "biz_unionid")
    @ApiModelProperty(value = "第三方系统unionid")
    @Schema(description = "第三方系统unionid")
    private String bizUnionid;

    private static final long serialVersionUID = 1L;
}
