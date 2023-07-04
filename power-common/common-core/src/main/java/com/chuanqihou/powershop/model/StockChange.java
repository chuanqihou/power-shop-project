package com.chuanqihou.powershop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/4 19:41
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("库存扣减信息")
public class StockChange {
    @ApiModelProperty("Prod表的库存扣减对象")
    List<ProdChange> prodChangeList;

    @ApiModelProperty("Sku表的库存扣减对象")
    List<SkuChange> skuChangeList;
}

