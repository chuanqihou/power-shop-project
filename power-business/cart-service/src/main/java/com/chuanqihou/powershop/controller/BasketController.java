package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.domain.Basket;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.BasketService;
import com.chuanqihou.powershop.vo.CartTotalMoneyVO;
import com.chuanqihou.powershop.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @PostMapping("/totalPay")
    public Result<CartTotalMoneyVO> getTotalPay(@RequestBody List<Long> basketId) {

        CartTotalMoneyVO cartTotalMoneyVO = basketService.findCartTotalPay(basketId);

        return Result.success(cartTotalMoneyVO);
    }

    @DeleteMapping("/deleteItem")
    public Result<Objects> eraseBasketByIds(@RequestBody List<Long> basketId) {
        basketService.removeBasketByIds(basketId);
        return Result.success();
    }

    @PostMapping("/changeItem")
    public Result<Object> editBasketNum(@RequestBody Basket basket) {
        basketService.modifyBasketNum(basket);
        return Result.success();
    }
}
