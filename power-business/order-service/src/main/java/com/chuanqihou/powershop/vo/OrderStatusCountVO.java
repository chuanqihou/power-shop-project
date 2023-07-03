package com.chuanqihou.powershop.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 传奇后
 * @date 2023/7/3 18:54
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单状态数量对象")
public class OrderStatusCountVO {

    @ApiModelProperty("待支付")
    private Long unPay = 0L;

    @ApiModelProperty("待发货")
    private Long payed = 0L;

    @ApiModelProperty("待收货")
    private Long consignment = 0L;

}

