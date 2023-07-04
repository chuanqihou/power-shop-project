package com.chuanqihou.powershop.feign;

import com.chuanqihou.powershop.domain.Basket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/4 18:47
 * @description
 */
@FeignClient("cart-service")
public interface OrderCartFeign {

    @GetMapping("/p/shopCart/getBasketListRemoteByBasketIds")
    List<Basket> getBasketListRemoteByBasketIds(@RequestParam("basketIds") List<Long> basketIds);

    @PostMapping("/p/shopCart/removeCartByOpenIdAndSkuId")
    void removeCartByOpenIdAndSkuId(@RequestParam("openId") String openId, @RequestBody List<Long> skuIdList);
}
