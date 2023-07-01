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

/**
 * 终端信息表
 */
@ApiModel(description = "终端信息表")
@Schema(description = "终端信息表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "oauth_client_details")
public class OauthClientDetails implements Serializable {
    @TableId(value = "client_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String clientId;

    @TableField(value = "client_secret")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String clientSecret;

    @TableField(value = "resource_ids")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String resourceIds;

    @TableField(value = "`scope`")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String scope;

    @TableField(value = "authorized_grant_types")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String authorizedGrantTypes;

    @TableField(value = "web_server_redirect_uri")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String webServerRedirectUri;

    @TableField(value = "authorities")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String authorities;

    @TableField(value = "access_token_validity")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Integer accessTokenValidity;

    @TableField(value = "refresh_token_validity")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Integer refreshTokenValidity;

    @TableField(value = "additional_information")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String additionalInformation;

    @TableField(value = "autoapprove")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private String autoapprove;

    private static final long serialVersionUID = 1L;
}