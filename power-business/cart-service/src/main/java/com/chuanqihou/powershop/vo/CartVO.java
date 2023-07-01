package com.chuanqihou.powershop.vo;

import com.chuanqihou.powershop.model.ShopCart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/1 20:46
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("购物车详情对象")
public class CartVO {

    @ApiModelProperty("店铺集合")
    private List<ShopCart> shopCarts;

    // 折扣  满减.

}

