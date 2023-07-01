package com.chuanqihou.powershop.vo;

import com.chuanqihou.powershop.domain.Prod;
import com.chuanqihou.powershop.domain.Sku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/1 15:13
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdVo extends Prod {

    /**
     * sku
     */
    private List<Sku> skuList;
}
