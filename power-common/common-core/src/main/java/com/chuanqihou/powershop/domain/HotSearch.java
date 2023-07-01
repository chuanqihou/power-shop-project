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
 * 热搜
 */
@ApiModel(description = "热搜")
@Schema(description = "热搜")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hot_search")
public class HotSearch implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "hot_search_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    @Schema(description = "主键")
    private Long hotSearchId;

    /**
     * 店铺ID 0为全局热搜
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value = "店铺ID 0为全局热搜")
    @Schema(description = "店铺ID 0为全局热搜")
    private Long shopId;

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "内容")
    @Schema(description = "内容")
    private String content;

    /**
     * 录入时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "录入时间")
    @Schema(description = "录入时间")
    private Date createTime;

    /**
     * 顺序
     */
    @TableField(value = "seq")
    @ApiModelProperty(value = "顺序")
    @Schema(description = "顺序")
    private Integer seq;

    /**
     * 状态 0下线 1上线
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态 0下线 1上线")
    @Schema(description = "状态 0下线 1上线")
    private Integer status;

    /**
     * 热搜标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "热搜标题")
    @Schema(description = "热搜标题")
    private String title;

    private static final long serialVersionUID = 1L;
}
