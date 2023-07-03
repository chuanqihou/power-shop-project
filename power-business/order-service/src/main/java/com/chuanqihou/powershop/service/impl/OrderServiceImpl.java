package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Order;
import com.chuanqihou.powershop.mapper.OrderMapper;
import com.chuanqihou.powershop.service.OrderService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.vo.OrderStatusCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderStatusCountVO findOrderStatusCount() {
        return orderMapper.selectOrderStatusCountByOpenId(AuthUtil.getLoginMemberOpenId());
    }
}
