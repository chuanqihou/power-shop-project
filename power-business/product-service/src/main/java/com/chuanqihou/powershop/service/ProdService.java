package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.Prod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.dto.ProdDTO;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface ProdService extends IService<Prod>{


    Page<Prod> findProdByPage(Page<Prod> page, Prod prod);

    boolean saveProd(ProdDTO prodDTO);
}
