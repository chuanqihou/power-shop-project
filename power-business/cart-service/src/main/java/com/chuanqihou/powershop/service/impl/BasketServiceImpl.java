package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Basket;
import com.chuanqihou.powershop.domain.Sku;
import com.chuanqihou.powershop.feign.SearchProductFeign;
import com.chuanqihou.powershop.mapper.BasketMapper;
import com.chuanqihou.powershop.model.CartItem;
import com.chuanqihou.powershop.model.ShopCart;
import com.chuanqihou.powershop.service.BasketService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.vo.CartTotalMoneyVO;
import com.chuanqihou.powershop.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class BasketServiceImpl extends ServiceImpl<BasketMapper, Basket> implements BasketService{

    @Autowired
    private BasketMapper basketMapper;

    @Autowired
    private SearchProductFeign searchProductFeign;

    @Override
    public Long findCartProdCount() {

        List<Basket> baskets = basketMapper.selectList(new LambdaQueryWrapper<Basket>()
                .eq(Basket::getOpenId, AuthUtil.getLoginMemberOpenId())
        );
        Long count = 0L;
        for (Basket basket : baskets) {
            count+=basket.getProdCount();
        }
        return count;
    }

    @Override
    public CartVO findCartProdListInfo() {
        // 获取当前登录用户openid
        String openId = AuthUtil.getLoginMemberOpenId();

        // 创建CartVo对象
        CartVO cartVO = new CartVO();

        // 根据openid 查询 basket 表中对应的购物车数据，记：basketList
        List<Basket> basketList = basketMapper.selectList(new LambdaQueryWrapper<Basket>()
                .eq(Basket::getOpenId, openId)
        );

        //RPC 根据skuId集合查询出对应的sku信息
        // 获取skuId集合
        List<Long> skuIdList = basketList.stream().map(Basket::getSkuId).collect(Collectors.toList());
        // RPC 远程调用
        List<Sku> skuAllList = searchProductFeign.getSkuListRemoteBySkuIds(skuIdList);

        // 创建 List<ShopCart>对象
        List<ShopCart> shopCartList = new ArrayList<>();

        // 处理basketList数据，根据 shopId 进行分组 记 Map<Long,List<Basket>> groupShopIdBasketList
        Map<Long, List<Basket>> groupShopIdBasketList = basketList.stream()
                .collect(Collectors.groupingBy(Basket::getShopId));

        // 遍历 groupShopIdBasketList
        groupShopIdBasketList.forEach((shopId, eachBasket) -> {
            // 在循环中创建 ShopCart对象 以及 List<CartItem> 对象
            ShopCart shopCart = new ShopCart();
            List<CartItem> cartItemList = new ArrayList<>();

            // 遍历 basketList
            basketList.forEach(basket -> {
                // 条件：basketList中的每一个Basket对象的shopId跟groupShopIdBasketList中的shopId相等
                if (basket.getShopId().equals(shopId)) {
                    // 在条件内创建 CartItem对象
                    CartItem cartItem = new CartItem();
                    //  通过复制Basket的属性以及远程调用product-service模块根据prodId查询相关sku信息并封装到carItem对象中
                    BeanUtils.copyProperties(basket, cartItem);
                    skuAllList.forEach(sku -> {
                        if (sku.getSkuId().equals(basket.getSkuId())) {
                            cartItem.setPic(sku.getPic());
                            cartItem.setPrice(sku.getPrice());
                            cartItem.setProdName(sku.getProdName());
                            cartItem.setSkuName(sku.getSkuName());
                        }
                    });
                    // 将cartItem对象添加到 cartItemList集合中
                    cartItemList.add(cartItem);
                }
            });

            // 将cartItemList集合 设置到 shopCart对象中
            shopCart.setShopCartItems(cartItemList);
            // 将shopCart设置到 List<ShopCart> 集合中
            shopCartList.add(shopCart);
        });

        // 将List<ShopCart> 集合 设置到 CartVo对象中
        cartVO.setShopCarts(shopCartList);
        // 返回CartVO对象
        return cartVO;
    }

    @Override
    public CartTotalMoneyVO findCartTotalPay(List<Long> basketId) {

        List<Basket> basketList = basketMapper.selectList(new LambdaQueryWrapper<Basket>()
                .in(Basket::getBasketId, basketId)
        );

        List<Long> skuIdList = basketList.stream().map(Basket::getSkuId).collect(Collectors.toList());

        // 远程调用 获取订单对应的sku的价格
        List<Sku> skuAllList = searchProductFeign.getSkuListRemoteBySkuIds(skuIdList);

        List<BigDecimal> totalList = new ArrayList<>();
        basketList.forEach(basket -> {
            skuAllList.forEach(sku -> {
                if (basket.getSkuId().equals(sku.getSkuId())) {
                    BigDecimal multiply = sku.getPrice().multiply(new BigDecimal(basket.getProdCount()));
                    totalList.add(multiply);
                }
            });
        });

        CartTotalMoneyVO cartTotalMoneyVO = new CartTotalMoneyVO();
        Optional<BigDecimal> reduce = totalList.stream().reduce(BigDecimal::add);
        if (reduce.isPresent()) {
            BigDecimal bigDecimal = reduce.get();
            cartTotalMoneyVO.setTotalMoney(bigDecimal);

            if (bigDecimal.compareTo(new BigDecimal(88)) < 0) {
                cartTotalMoneyVO.setTransMoney(new BigDecimal(12));
            }

            cartTotalMoneyVO.setFinalMoney(bigDecimal.add(cartTotalMoneyVO.getTransMoney()));

        }

        return cartTotalMoneyVO;
    }

    @Override
    public void removeBasketByIds(List<Long> basketIds) {
        int delete = basketMapper.delete(new LambdaQueryWrapper<Basket>()
                .in(Basket::getBasketId, basketIds)
        );
        if (delete == 0) {
            throw new RuntimeException("移除购物车商品失败！");
        }
    }

    @Override
    public void modifyBasketNum(Basket basket) {
        Basket bk = basketMapper.selectOne(new LambdaQueryWrapper<Basket>()
                .eq(Basket::getSkuId, basket.getSkuId())
                .eq(Basket::getOpenId, AuthUtil.getLoginMemberOpenId())
        );
        bk.setProdCount(bk.getProdCount() + basket.getProdCount());
        int updateById = basketMapper.updateById(bk);
        if (updateById != 1) {
            throw new RuntimeException("更新失败！");
        }
    }
}
