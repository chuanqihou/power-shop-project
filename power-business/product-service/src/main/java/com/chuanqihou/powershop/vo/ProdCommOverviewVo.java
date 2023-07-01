package com.chuanqihou.powershop.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 传奇后
 * @date 2023/7/1 16:57
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论的总览对象")
public class ProdCommOverviewVo {

    @ApiModelProperty("好评率")
    private BigDecimal positiveRating = BigDecimal.ZERO;
    @ApiModelProperty("总评数")
    private Integer number = 0;
    @ApiModelProperty("好评数")
    private Integer praiseNumber = 0;
    @ApiModelProperty("中评数")
    private Integer secondaryNumber = 0;
    @ApiModelProperty("差评数")
    private Integer negativeNumber = 0;
    @ApiModelProperty("带图数")
    private Integer picNumber = 0;

}

