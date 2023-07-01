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
@ApiModel(description = "attach_file")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "attach_file")
public class AttachFile implements Serializable {
    @TableId(value = "file_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    @Schema(description = "")
    private Long fileId;

    /**
     * 文件路径
     */
    @TableField(value = "file_path")
    @ApiModelProperty(value = "文件路径")
    @Schema(description = "文件路径")
    private String filePath;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    @ApiModelProperty(value = "文件类型")
    @Schema(description = "文件类型")
    private String fileType;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    @ApiModelProperty(value = "文件大小")
    @Schema(description = "文件大小")
    private Integer fileSize;

    /**
     * 上传时间
     */
    @TableField(value = "upload_time")
    @ApiModelProperty(value = "上传时间")
    @Schema(description = "上传时间")
    private Date uploadTime;

    /**
     * 文件关联的表主键id
     */
    @TableField(value = "file_join_id")
    @ApiModelProperty(value = "文件关联的表主键id")
    @Schema(description = "文件关联的表主键id")
    private Long fileJoinId;

    /**
     * 文件关联表类型：1 商品表  FileJoinType
     */
    @TableField(value = "file_join_type")
    @ApiModelProperty(value = "文件关联表类型：1 商品表  FileJoinType")
    @Schema(description = "文件关联表类型：1 商品表  FileJoinType")
    private Integer fileJoinType;

    private static final long serialVersionUID = 1L;
}
