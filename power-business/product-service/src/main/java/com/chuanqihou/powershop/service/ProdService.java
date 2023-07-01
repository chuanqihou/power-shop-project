package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.Prod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.dto.ProdDTO;
import com.chuanqihou.powershop.model.ProdEs;
import com.chuanqihou.powershop.vo.ProdVo;

import java.util.Date;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface ProdService extends IService<Prod>{


    Page<Prod> findProdByPage(Page<Prod> page, Prod prod);

    void saveProd(ProdDTO prodDTO);

    ProdDTO getProdById(Long prodId);

    void modifyProd(ProdDTO prodDTO);

    void removeProdBuProdId(Long prodId);

    int selectProdCount(Date t1, Date t2);

    List<ProdEs> selectProdEsByPage(int currentPage, int size, Date t1, Date t2);

    ProdVo findProdInfoByProdId(Long prodId);
}
