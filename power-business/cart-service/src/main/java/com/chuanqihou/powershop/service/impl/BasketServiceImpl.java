package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Basket;
import com.chuanqihou.powershop.mapper.BasketMapper;
import com.chuanqihou.powershop.model.CartItem;
import com.chuanqihou.powershop.model.ShopCart;
import com.chuanqihou.powershop.service.BasketService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                    // TODO: 通过复制Basket的属性以及远程调用product-service模块根据prodId查询相关信息并封装到carItem对象中
                    BeanUtils.copyProperties(basket, cartItem);
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
}
