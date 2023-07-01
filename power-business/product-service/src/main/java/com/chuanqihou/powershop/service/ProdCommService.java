package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdComm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.vo.ProdCommOverviewVo;
import com.chuanqihou.powershop.vo.ProdCommVO;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface ProdCommService extends IService<ProdComm>{

    Page<ProdComm> findProCommPage(Page<ProdComm> page, ProdComm prodComm);

    void modifyProdComm(ProdComm prodComm);

    ProdCommOverviewVo findProdCommCount(Long prodId);

    Page<ProdCommVO> findWxProdCommCount(Page<ProdComm> page, ProdComm prodComm);
}
