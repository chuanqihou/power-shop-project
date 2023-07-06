package com.chuanqihou.powershop.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.*;
import com.chuanqihou.powershop.dto.OrderConfirmDTO;
import com.chuanqihou.powershop.feign.OrderCartFeign;
import com.chuanqihou.powershop.feign.OrderMemberFeign;
import com.chuanqihou.powershop.feign.OrderProductFeign;
import com.chuanqihou.powershop.mapper.OrderMapper;
import com.chuanqihou.powershop.model.ProdChange;
import com.chuanqihou.powershop.model.ShopCartOrder;
import com.chuanqihou.powershop.model.SkuChange;
import com.chuanqihou.powershop.model.StockChange;
import com.chuanqihou.powershop.service.OrderItemService;
import com.chuanqihou.powershop.service.OrderService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.vo.OrderConfirmVO;
import com.chuanqihou.powershop.vo.OrderStatusCountVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderMemberFeign orderMemberFeign;

    @Autowired
    private OrderProductFeign orderProductFeign;

    @Autowired
    private OrderCartFeign orderCartFeign;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public OrderStatusCountVO findOrderStatusCount() {
        return orderMapper.selectOrderStatusCountByOpenId(AuthUtil.getLoginMemberOpenId());
    }

    @Override
    public OrderConfirmVO confirmOrder(OrderConfirmDTO orderConfirmDTO) {
        // 获取商品详情对象 记 orderItem
        OrderItem orderItem = orderConfirmDTO.getOrderItem();

        // 创建OrderConfirmVO对象 记 orderConfirmVO
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();

        // 通过用户openid 远程调用member-service服务，获取用户的默认收货地址 记 memberAddr
        MemberAddr memberAddr = orderMemberFeign.getMemberAddrRemoteByOpenId(AuthUtil.getLoginMemberOpenId(),orderConfirmDTO.getAddrId());
        // 将memberAddr设置到orderConfirmVO对象中
        orderConfirmVO.setMemberAddr(memberAddr);

        //  判断该对象，区分入口（商品详情页、购物车页面）
        if (!ObjectUtils.isEmpty(orderItem)) {
            // 用户从商品详情页确定订单

            // 创建List<ShopCartOrder>店铺集合 记 shopCartOrderList
            List<ShopCartOrder> shopCartOrderList = new ArrayList<>();
            // 创建ShopCartOrder对象 记 shopCartOrder
            ShopCartOrder shopCartOrder = new ShopCartOrder();
            // 创建List<OrderItem>商品条目集合 记 shopCartItemDiscounts
            List<OrderItem> shopCartItemDiscounts = new ArrayList<>();
            // 通过orderItem对象获取skuId
            Long skuId = orderItem.getSkuId();
            // 远程调用 product-service 服务 同skuId获取Sku对象 记sku
            Sku sku = orderProductFeign.getSkuListRemoteBySkuIds(Arrays.asList(skuId)).get(0);
            // 将sku对象中的属性复制到orderItem对象中
            BeanUtils.copyProperties(sku, orderItem);
            // 将orderItem对象添加到shopCartItemDiscounts集合中
            shopCartItemDiscounts.add(orderItem);
            // 将shopCartItemDiscount对象添加到 shopCartOrder对象中
            shopCartOrder.setShopCartItemDiscounts(shopCartItemDiscounts);
            // 将shopCartOrder对象添加到 shopCartOrderList店铺集合中
            shopCartOrderList.add(shopCartOrder);
            // 将shopCartOrderList设置到orderConfirmVO对象中
            orderConfirmVO.setShopCartOrders(shopCartOrderList);

            // 从orderItem中获取订单商品总数量
            Integer prodCount = orderItem.getProdCount();
            orderConfirmVO.setTotalCount(prodCount);

            // 计算商品总金额
            BigDecimal totalMoney = orderItem.getPrice().multiply(new BigDecimal(prodCount));

            BigDecimal transfee = new BigDecimal(0);
            // 判断是否需要运费，需要则设置
            if (totalMoney.compareTo(new BigDecimal(88)) < 0) {
                transfee = new BigDecimal(12);
                orderConfirmVO.setTransfee(transfee);
            }

            // 计算订单的实际总金额
            BigDecimal actualTotal = totalMoney.add(transfee);

            // 将商品总金额、实际总金额添加到orderConfirmVO对象中
            orderConfirmVO.setTotalMoney(totalMoney);
            orderConfirmVO.setActualTotal(actualTotal);

            return orderConfirmVO;

        }

        // 获取所有购物车id结合
        List<Long> basketIds = orderConfirmDTO.getBasketIds();

        // 远程调用 RPC 根据basketIds查询 出对应的basket对象
        List<Basket> basketList = orderCartFeign.getBasketListRemoteByBasketIds(basketIds);

        List<Long> skuIdList = basketList.stream().map(Basket::getSkuId).collect(Collectors.toList());

        List<Sku> skuList = orderProductFeign.getSkuListRemoteBySkuIds(skuIdList);

        Map<Long, List<Basket>> groupBasketListByShopId = basketList.stream().collect(Collectors.groupingBy(Basket::getShopId));

        // 创建List<ShopCartOrder>店铺集合 记 shopCartOrderList
        List<ShopCartOrder> shopCartOrderList = new ArrayList<>();

        List<Integer> prodCountList = new ArrayList<>();
        List<BigDecimal> totalMoneyList = new ArrayList<>();

        groupBasketListByShopId.forEach((shopId,eachBasketList)->{
            ShopCartOrder shopCartOrder = new ShopCartOrder();
            List<OrderItem> orderItemList = new ArrayList<>();

            eachBasketList.forEach(basket -> {
                skuList.forEach(sku -> {
                    if (sku.getSkuId().equals(basket.getSkuId())) {
                        OrderItem orderIt = new OrderItem();
                        BeanUtils.copyProperties(basket, orderIt);
                        BeanUtils.copyProperties(sku, orderIt);
                        orderItemList.add(orderIt);

                        prodCountList.add(orderIt.getProdCount());
                        totalMoneyList.add(orderIt.getPrice().multiply(new BigDecimal(orderIt.getProdCount())));
                    }
                });
            });

            shopCartOrder.setShopCartItemDiscounts(orderItemList);
            shopCartOrderList.add(shopCartOrder);
        });

        // 将shopCartOrderList设置到orderConfirmVO对象中
        orderConfirmVO.setShopCartOrders(shopCartOrderList);

        // 从orderItem中获取订单商品总数量
        Integer prodCount = prodCountList.stream().mapToInt(Integer::intValue).sum();
        orderConfirmVO.setTotalCount(prodCount);

        // 计算商品总金额
        BigDecimal totalMoney = totalMoneyList.stream().reduce(BigDecimal::add).get();

        BigDecimal transfee = new BigDecimal(0);
        // 判断是否需要运费，需要则设置
        if (totalMoney.compareTo(new BigDecimal(88)) < 0) {
            transfee = new BigDecimal(12);
            orderConfirmVO.setTransfee(transfee);
        }

        // 计算订单的实际总金额
        BigDecimal actualTotal = totalMoney.add(transfee);

        // 将商品总金额、实际总金额添加到orderConfirmVO对象中
        orderConfirmVO.setTotalMoney(totalMoney);
        orderConfirmVO.setActualTotal(actualTotal);

        return orderConfirmVO;

    }

    /**
     * 提交订单
     * @param orderConfirmVO 提交数据
     * @return 订单编号
     */
    @Override
    public String submitOrder(OrderConfirmVO orderConfirmVO) {

        // 生成唯一订单编号（这里使用雪花算法）
        String orderNum = generateOrderNumber();

        // 将对应购物车内的以确定的订单商品移除
        cleanCart(orderConfirmVO.getShopCartOrders());

        // 扣减库存（mysql）
        StockChange stockChange = changeMysqlStock(orderConfirmVO.getShopCartOrders());

        // 扣减库存（ES）
        changeEsStock(stockChange.getProdChangeList());

        // 封装订单数据，往订单表和订单详细表中插入数据
        saveOrder(orderConfirmVO,AuthUtil.getLoginMemberOpenId(),orderNum,stockChange);

        // 延迟队列，若订单未支付、取消等需要回滚数据（库存）
        checkBackStock(stockChange,orderNum);

        return orderNum;
    }

    /**
     * 延迟队列，若订单未支付、取消等需要回滚数据（库存）
     * @param stockChange 库存对象
     * @param orderNum 订单编号
     */
    private void checkBackStock(StockChange stockChange, String orderNum) {
        // 创建Message对象，封装stockChange对象已经orderNum
        Message<String> message = MessageBuilder
                .withPayload(JSON.toJSONString(stockChange))
                .setHeader(RocketMQHeaders.KEYS,orderNum)
                .build();

        // 发送异步延迟消息，回滚数据（30分钟后执行）
        rocketMQTemplate.asyncSend("order-check-back-stock-topic", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("【order-check-back-stock-topic】发送消息成功");
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("【order-check-back-stock-topic】发送消息失败");
            }
        }, 5000, 16);
    }


    /**
     * 封装订单数据，往订单表和订单详细表中插入数据
     * @param orderConfirmVO 前端提交的数据
     * @param loginMemberOpenId 登录用户openId
     * @param orderNum 订单号
     * @param stockChange 库存对象
     */
    private void saveOrder(OrderConfirmVO orderConfirmVO, String loginMemberOpenId,String orderNum,StockChange stockChange) {
        // 往 Order表中插入一条订单数据
        // 获取订单商品总数
        List<Integer> prodNumList = stockChange.getProdChangeList().stream().map(ProdChange::getProdCount).collect(Collectors.toList());
        Integer prodNum = prodNumList.stream().mapToInt(Integer::intValue).sum();
        // 创建一个Order对象 记 order 封装order数据
        Order order = Order.builder()
                .openId(loginMemberOpenId)
                .orderNumber(orderNum)
                //.totalMoney()
                //.actualTotal()
                .remarks(orderConfirmVO.getRemarks())
                .status(1)
                .addrOrderId(orderConfirmVO.getMemberAddr().getAddrId())
                .productNums(prodNum)
                .createTime(new Date())
                .updateTime(new Date())
                .isPayed(false)
                .deleteStatus(0)
                .build();

        // 往order_item（订单详情表）中插入数据

        // 获取skuIdList
        List<Long> skuIdList = stockChange.getSkuChangeList().stream().map(SkuChange::getSkuId).collect(Collectors.toList());
        // RPC 根据skuIdList 获取 SkuList集合
        List<Sku> skuList = orderProductFeign.getSkuListRemoteBySkuIds(skuIdList);

        // 记录总金额集合
        List<BigDecimal> totalMoneyList = new ArrayList<>();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderConfirmVO.getShopCartOrders().forEach(shopCartOrder -> {
            // 遍历 orderItem
            shopCartOrder.getShopCartItemDiscounts().forEach(orderItem -> {
                skuList.forEach(sku -> {
                    if (sku.getSkuId().equals(orderItem.getSkuId())) {
                        // 复制 sku 属性到 orderItem
                        BeanUtils.copyProperties(sku, orderItem);
                        orderItem.setOrderNumber(orderNum);
                        orderItem.setProductTotalAmount(sku.getPrice().multiply(new BigDecimal(orderItem.getProdCount())));
                        // 获取商品金额,并添加
                        totalMoneyList.add(orderItem.getPrice());
                        // 将orderItem添加到orderItemList集合中
                        orderItemList.add(orderItem);
                    }
                });
            });
        });
        // 封装金额
        BigDecimal totalMoney = totalMoneyList.stream().reduce(BigDecimal::add).get();
        order.setTotalMoney(totalMoney);
        order.setActualTotal(totalMoney);
        // 执行插入
        orderMapper.insert(order);

        // 执行批量保存商品详情信息
        orderItemService.saveBatch(orderItemList);

    }

    /**
     * 使用消息队列异步更新es库存
     * @param prodChangeList 店铺订单对象集合
     */
    private void changeEsStock(List<ProdChange> prodChangeList) {
        rocketMQTemplate.asyncSend("prodEs-update-topic", prodChangeList, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("【prodEs-update-topic】消息发送成功");
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("【prodEs-update-topic】消息发送失败");
            }
        });
    }



    /**
     * 移除购物车中已经提交订单的商品
     * @param shopCartOrders 店铺订单对象集合
     */
    private void cleanCart(List<ShopCartOrder> shopCartOrders) {
        List<Long> skuIdList = new ArrayList<>();
        shopCartOrders.forEach(shopCartOrder -> {
            List<OrderItem> shopCartItemDiscounts = shopCartOrder.getShopCartItemDiscounts();
            shopCartItemDiscounts.forEach(orderItem -> {
                skuIdList.add(orderItem.getSkuId());
            });
        });
        if (CollectionUtils.isEmpty(skuIdList)) {
            return;
        }
        String openId = AuthUtil.getLoginMemberOpenId();
        // RPC 远程调用 cart-service 服务 根据skuId移除购物车中的商品
        orderCartFeign.removeCartByOpenIdAndSkuId(openId, skuIdList);
    }

    /**
     * 扣减库存（mysql）
     * @param shopCartOrders 店铺订单对象
     * @return 库存扣减信息
     */
    private StockChange changeMysqlStock(List<ShopCartOrder> shopCartOrders) {
        StockChange stockChange = new StockChange();
        List<ProdChange> prodChangeList = new ArrayList<>();
        List<SkuChange> skuChangeList = new ArrayList<>();

        // 封装信息到prodChangeList 和 skuChangeList
        shopCartOrders.forEach(shopCartOrder -> {
            // 获取 商品条目集合
            List<OrderItem> shopCartItemDiscounts = shopCartOrder.getShopCartItemDiscounts();
            // 遍历 商品条目集合
            shopCartItemDiscounts.forEach(orderItem -> {
                // 获取商品购买数量
                Integer prodCount = orderItem.getProdCount() * -1;
                // 获取商品的skuId
                Long skuId = orderItem.getSkuId();
                // 封装 skuId 和 prodCount 到 skuChange对象中
                SkuChange skuChange = new SkuChange();
                skuChange.setSkuId(skuId);
                skuChange.setProdCount(prodCount);
                skuChangeList.add(skuChange);
                // 对prodChangeList进行过滤，判断orderItem中的prodId对应的ProdChange是否已经在prodChanges集合中存在
                List<ProdChange> prodChanges = prodChangeList.stream().filter(prodChange -> prodChange.getProdId().equals(orderItem.getProdId())).collect(Collectors.toList());
                // 根据商品id判断是否第一次添加
                if (CollectionUtils.isEmpty(prodChanges)) {
                    // 如果是第一次添加该商品，则创建prodChange对象
                    ProdChange prodChange = new ProdChange();
                    // 直接封装 prodId 和 prodCount 信息
                    prodChange.setProdId(orderItem.getProdId());
                    prodChange.setProdCount(prodCount);
                    // 添加到集合中
                    prodChangeList.add(prodChange);
                }else {
                    // 如果 prodChangeList集合中已有该商品id，则直接获取该prodChange对象
                    ProdChange prodChange = prodChanges.get(0);
                    // 重新设置该商品购买的总数量（记该商品下所有sku的购买数量之和）
                    prodChange.setProdCount(prodChange.getProdCount() + prodCount);
                }
            });
        });
        // 封装到stockChange对象中
        stockChange.setProdChangeList(prodChangeList);
        stockChange.setSkuChangeList(skuChangeList);

        // RPC 远程调用，修改prod表和sku表对应商品的库存信息
        orderProductFeign.changeStock(stockChange);

        // 还原 prodCount（将prodCount还原成正数）
        stockChange.getSkuChangeList().forEach(skuChange -> {
            skuChange.setProdCount(skuChange.getProdCount() * -1);
        });
        stockChange.getProdChangeList().forEach(prodChange -> {
            prodChange.setProdCount(prodChange.getProdCount() * -1);
        });

        // 返回
        return stockChange;
    }

    /**
     * 使用雪花算法生成订单编号
     * @return 订单编号
     */
    private String generateOrderNumber() {
        return snowflake.nextIdStr();
    }

}
