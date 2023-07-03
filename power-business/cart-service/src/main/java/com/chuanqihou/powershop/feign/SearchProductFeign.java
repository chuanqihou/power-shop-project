package com.chuanqihou.powershop.feign;

import com.chuanqihou.powershop.domain.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/2 17:12
 * @description
 */
@FeignClient("product-service")
public interface SearchProductFeign {

    @PostMapping("/prod/prod/getSkuListRemoteBySkuIds")
    List<Sku> getSkuListRemoteBySkuIds(@RequestBody List<Long> skuIds);

}
