package com.chuanqihou.powershop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanqihou.powershop.domain.Sku;
import com.chuanqihou.powershop.model.SkuChange;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {
    void updateSkuStockBySkuId(SkuChange skuChange);
}
