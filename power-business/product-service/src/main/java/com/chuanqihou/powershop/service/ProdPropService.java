package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdProp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.domain.ProdPropValue;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface ProdPropService extends IService<ProdProp>{


    Page<ProdProp> findProdPropAndValueByPage(Page<ProdProp> page, ProdProp prodProp);

    List<ProdProp> findProdPropAll();

    List<ProdPropValue> findProdPropValueByPropId(Long prodId);
}
