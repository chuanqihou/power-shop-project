package com.chuanqihou.powershop.model;

import com.chuanqihou.powershop.domain.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/4 10:28
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("店铺订单对象")
public class ShopCartOrder {

    @ApiModelProperty("商品条目集合")
    private List<OrderItem> shopCartItemDiscounts;

    @ApiModelProperty("店铺满减")
    private BigDecimal shopReduce;

    @ApiModelProperty("店铺运费")
    private BigDecimal transfee;

}

