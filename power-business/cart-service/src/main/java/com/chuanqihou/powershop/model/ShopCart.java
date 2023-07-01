package com.chuanqihou.powershop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/1 20:45
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("店铺对象")
public class ShopCart {
    @ApiModelProperty("商品集合")
    private List<CartItem> shopCartItems;

    // shopName  id 折扣....

}

