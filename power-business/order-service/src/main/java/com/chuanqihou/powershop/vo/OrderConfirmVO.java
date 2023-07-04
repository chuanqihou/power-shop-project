package com.chuanqihou.powershop.vo;

import com.chuanqihou.powershop.domain.MemberAddr;
import com.chuanqihou.powershop.model.ShopCartOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/4 10:27
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单确认的返回对象")
public class OrderConfirmVO {

    @ApiModelProperty("用户的收货地址")
    private MemberAddr memberAddr;

    @ApiModelProperty("店铺集合")
    private List<ShopCartOrder> shopCartOrders;

    @ApiModelProperty("订单商品总数量")
    private Integer totalCount;

    @ApiModelProperty("实际金额")
    private BigDecimal actualTotal;

    @ApiModelProperty("总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("满减")
    private BigDecimal shopReduce;

    @ApiModelProperty("运费")
    private BigDecimal transfee;

    @ApiModelProperty("买家留言")
    private String remarks;

    @ApiModelProperty("选择的优惠券id")
    private List<Long> couponIds;

}
