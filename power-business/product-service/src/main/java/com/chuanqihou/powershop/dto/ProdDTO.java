package com.chuanqihou.powershop.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.chuanqihou.powershop.domain.Prod;
import com.chuanqihou.powershop.domain.Sku;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/27 20:13
 * @description 商品信息DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdDTO extends Prod {

    /**
     * 商品标签信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "商品标签信息")
    private List<Long> tagList;

    /**
     * 商品sku信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "商品sku信息")
    private List<Sku> skuList;

    /**
     * 商品配送信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "商品配送信息")
    private DeliveryMode deliveryModeVo;

    /**
     * 商品配置类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeliveryMode {
        /**
         * 商家配送
         */
        @TableField(exist = false)
        @ApiModelProperty(value = "商家配送")
        private Boolean hasShopDelivery;

        /**
         * 自提
         */
        @TableField(exist = false)
        @ApiModelProperty(value = "自提")
        private Boolean hasUserPickUp;
    }


}
