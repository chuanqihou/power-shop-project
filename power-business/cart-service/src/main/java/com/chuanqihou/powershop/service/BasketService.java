package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.domain.Basket;
import com.chuanqihou.powershop.vo.CartTotalMoneyVO;
import com.chuanqihou.powershop.vo.CartVO;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface BasketService extends IService<Basket>{


    Long findCartProdCount();

    CartVO findCartProdListInfo();

    CartTotalMoneyVO findCartTotalPay(List<Long> basketId);

    void removeBasketByIds(List<Long> basketId);

    void modifyBasketNum(Basket basket);
}
