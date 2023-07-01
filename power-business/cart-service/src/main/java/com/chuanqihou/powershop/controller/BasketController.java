package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.BasketService;
import com.chuanqihou.powershop.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/7/1 20:32
 * @description
 */
@RestController
@RequestMapping("/p/shopCart")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping("/prodCount")
    public Result<Long> getProdCount() {
        Long count = basketService.findCartProdCount();
        return Result.success(count);
    }

    @GetMapping("/info")
    public Result<CartVO> getCartProdInfo() {
        CartVO cartVO = basketService.findCartProdListInfo();
        return Result.success(cartVO);
    }

}
