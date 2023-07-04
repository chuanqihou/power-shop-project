package com.chuanqihou.powershop.service;

import com.chuanqihou.powershop.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.dto.OrderConfirmDTO;
import com.chuanqihou.powershop.vo.OrderConfirmVO;
import com.chuanqihou.powershop.vo.OrderStatusCountVO;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface OrderService extends IService<Order>{


    OrderStatusCountVO findOrderStatusCount();

    OrderConfirmVO confirmOrder(OrderConfirmDTO orderConfirmDTO);

    String submitOrder(OrderConfirmVO orderConfirmVO);
}
