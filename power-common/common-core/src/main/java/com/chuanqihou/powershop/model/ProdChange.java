package com.chuanqihou.powershop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 传奇后
 * @date 2023/7/4 19:42
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Prod表的库存扣减对象")
public class ProdChange {

    @ApiModelProperty("商品id")
    private Long prodId;

    @ApiModelProperty("扣减数量")
    private Integer prodCount;

}

