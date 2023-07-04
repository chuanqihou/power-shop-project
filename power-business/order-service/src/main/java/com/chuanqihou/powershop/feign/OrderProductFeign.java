package com.chuanqihou.powershop.feign;

import com.chuanqihou.powershop.domain.Sku;
import com.chuanqihou.powershop.model.StockChange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/4 14:48
 * @description
 */
@FeignClient("product-service")
public interface OrderProductFeign {

    @PostMapping("/prod/prod/getSkuListRemoteBySkuIds")
    List<Sku> getSkuListRemoteBySkuIds(@RequestBody List<Long> skuIds);

    @PostMapping("/prod/prod/changeStock")
    void changeStock(@RequestBody StockChange stockChange);
}
