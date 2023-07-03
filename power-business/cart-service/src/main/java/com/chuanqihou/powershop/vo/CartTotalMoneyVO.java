package com.chuanqihou.powershop.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 传奇后
 * @date 2023/7/3 14:40
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("购物车总金额")
public class CartTotalMoneyVO {

    @ApiModelProperty("最终金额")
    private BigDecimal finalMoney = BigDecimal.ZERO;
    @ApiModelProperty("满减金额")
    private BigDecimal subtractMoney = BigDecimal.ZERO;
    @ApiModelProperty("原始总金额")
    private BigDecimal totalMoney = BigDecimal.ZERO;
    @ApiModelProperty("运费金额")
    private BigDecimal transMoney = BigDecimal.ZERO;

}

