package com.chuanqihou.powershop.dto;

import com.chuanqihou.powershop.domain.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/4 10:33
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单确认对象")
public class OrderConfirmDTO {

    @ApiModelProperty("购物车的ids")
    private List<Long> basketIds;

    @ApiModelProperty("商品详情对象")
    private OrderItem orderItem;

    private Long addrId;

}

