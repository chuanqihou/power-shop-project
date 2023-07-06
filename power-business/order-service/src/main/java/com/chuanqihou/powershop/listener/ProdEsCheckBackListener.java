package com.chuanqihou.powershop.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.domain.Order;
import com.chuanqihou.powershop.feign.OrderProductFeign;
import com.chuanqihou.powershop.mapper.OrderMapper;
import com.chuanqihou.powershop.model.StockChange;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author 传奇后
 * @date 2023/7/6 11:43
 * @description
 */
@RocketMQMessageListener(
        topic = "order-check-back-stock-topic",
        consumerGroup = "order_consumer_check_back_stock_group"
)
@Component
public class ProdEsCheckBackListener implements RocketMQListener<MessageExt> {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderProductFeign orderProductFeign;

    @Override
    public void onMessage(MessageExt messageExt) {
        // 获取 stockChange 和 orderNum
        String stockChangeJson = new String(messageExt.getBody());
        StockChange stockChange = JSON.parseObject(stockChangeJson, StockChange.class);
        String orderNum = messageExt.getKeys();
        // 根据订单编号查询订单状态
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNumber, orderNum)
        );
        // 如果order为null 或者 订单状态为 已支付 则结束该方法
        if (ObjectUtils.isEmpty(order) || order.getStatus().equals(1)) {
            return;
        }
        // 程序执行到这里，代表 order表中的orderNum该订单状态为 未支付
        // 调用第三方支付接口，根据订单编号验证该订单是否已支付
        boolean isPay = false;
        if (isPay) {
            // 如果已经支付，则修改订单状态为已支付
            order.setStatus(1);
            order.setUpdateTime(new Date());
            order.setPayTime(new Date());
            // 调用更新操作
            orderMapper.updateById(order);
        }
        // 代码走到这里，说明订单一定没有支付，RPC 远程调用 回滚库存
        orderProductFeign.changeStock(stockChange);
    }
}
