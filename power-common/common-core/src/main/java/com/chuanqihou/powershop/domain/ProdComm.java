package com.chuanqihou.powershop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */

/**
 * 商品评论
 */
@ApiModel(description = "商品评论")
@Schema(description = "商品评论")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "prod_comm")
public class ProdComm implements Serializable {
    /**
     * ID
     */
    @TableId(value = "prod_comm_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    @Schema(description = "ID")
    private Long prodCommId;

    /**
     * 商品ID
     */
    @TableField(value = "prod_id")
    @ApiModelProperty(value = "商品ID")
    @Schema(description = "商品ID")
    private Long prodId;

    /**
     * 商品的名字
     */
    @TableField(value = "prod_name")
    @ApiModelProperty(value = "商品的名字")
    @Schema(description = "商品的名字")
    private String prodName;

    /**
     * 订单项ID
     */
    @TableField(value = "order_item_id")
    @ApiModelProperty(value = "订单项ID")
    @Schema(description = "订单项ID")
    private Long orderItemId;

    /**
     * 评论用户ID
     */
    @TableField(value = "open_id")
    @ApiModelProperty(value = "评论用户ID")
    @Schema(description = "评论用户ID")
    private String openId;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "评论内容")
    @Schema(description = "评论内容")
    private String content;

    /**
     * 掌柜回复
     */
    @TableField(value = "reply_content")
    @ApiModelProperty(value = "掌柜回复")
    @Schema(description = "掌柜回复")
    private String replyContent;

    /**
     * 记录时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "记录时间")
    @Schema(description = "记录时间")
    private Date createTime;

    /**
     * 回复时间
     */
    @TableField(value = "reply_time")
    @ApiModelProperty(value = "回复时间")
    @Schema(description = "回复时间")
    private Date replyTime;

    /**
     * 是否回复 0:未回复  1:已回复
     */
    @TableField(value = "reply_sts")
    @ApiModelProperty(value = "是否回复 0:未回复  1:已回复")
    @Schema(description = "是否回复 0:未回复  1:已回复")
    private Integer replySts;

    /**
     * IP来源
     */
    @TableField(value = "postip")
    @ApiModelProperty(value = "IP来源")
    @Schema(description = "IP来源")
    private String postip;

    /**
     * 得分，0-5分
     */
    @TableField(value = "score")
    @ApiModelProperty(value = "得分，0-5分")
    @Schema(description = "得分，0-5分")
    private Integer score;

    /**
     * 有用的计数
     */
    @TableField(value = "useful_counts")
    @ApiModelProperty(value = "有用的计数")
    @Schema(description = "有用的计数")
    private Integer usefulCounts;

    /**
     * 晒图的json字符串
     */
    @TableField(value = "pics")
    @ApiModelProperty(value = "晒图的json字符串")
    @Schema(description = "晒图的json字符串")
    private String pics;

    /**
     * 是否匿名(1:是  0:否)
     */
    @TableField(value = "is_anonymous")
    @ApiModelProperty(value = "是否匿名(1:是  0:否)")
    @Schema(description = "是否匿名(1:是  0:否)")
    private Integer isAnonymous;

    /**
     * 是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1")
    @Schema(description = "是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1")
    private Integer status;

    /**
     * 评价(0好评 1中评 2差评)
     */
    @TableField(value = "evaluate")
    @ApiModelProperty(value = "评价(0好评 1中评 2差评)")
    @Schema(description = "评价(0好评 1中评 2差评)")
    private Integer evaluate;

    /**
     * 店铺
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value = "店铺")
    @Schema(description = "店铺")
    private Long shopId;

    private static final long serialVersionUID = 1L;
}
