package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.dto.OrderConfirmDTO;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.OrderService;
import com.chuanqihou.powershop.vo.OrderConfirmVO;
import com.chuanqihou.powershop.vo.OrderStatusCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 传奇后
 * @date 2023/7/3 18:53
 * @description
 */
@RestController
@RequestMapping("/p/myOrder")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderCount")
    public Result<OrderStatusCountVO> getOrderCount() {
        OrderStatusCountVO orderStatusCountVO = orderService.findOrderStatusCount();
        return Result.success(orderStatusCountVO);
    }

    @PostMapping("/confirm")
    public Result<OrderConfirmVO> confirmOrder(@RequestBody OrderConfirmDTO orderConfirmDTO) {
        OrderConfirmVO orderConfirmVO = orderService.confirmOrder(orderConfirmDTO);
        return Result.success(orderConfirmVO);
    }

    @PostMapping("/submit")
    public Result submitOrder(@RequestBody OrderConfirmVO orderConfirmVO) {
        String orderNum = orderService.submitOrder(orderConfirmVO);
        return Result.success(orderNum);
    }

}
