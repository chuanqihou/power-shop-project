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
@ApiModel(description = "hibernate_sequence")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hibernate_sequence")
public class HibernateSequence implements Serializable {
    @TableField(value = "next_val")
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long nextVal;

    private static final long serialVersionUID = 1L;
}