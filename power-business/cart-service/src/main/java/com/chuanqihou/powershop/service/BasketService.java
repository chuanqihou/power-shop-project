package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.domain.Basket;
import com.chuanqihou.powershop.vo.CartVO;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface BasketService extends IService<Basket>{


    Long findCartProdCount();

    CartVO findCartProdListInfo();

}
