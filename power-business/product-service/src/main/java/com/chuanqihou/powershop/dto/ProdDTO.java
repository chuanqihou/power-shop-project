package com.chuanqihou.powershop.dto;

import com.chuanqihou.powershop.domain.Prod;
import com.chuanqihou.powershop.domain.Sku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/27 20:13
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdDTO extends Prod {

    private List<Long> tagList;

    private List<Sku> skuList;

    private DeliveryMode deliveryModeVo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeliveryMode {

        private Boolean hasShopDelivery;

        private Boolean hasUserPickUp;
    }


}
