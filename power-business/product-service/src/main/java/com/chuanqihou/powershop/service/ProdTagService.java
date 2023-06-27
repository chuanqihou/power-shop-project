package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.model.Result;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface ProdTagService extends IService<ProdTag>{


    Page<ProdTag> findProdTagByPage(Page<ProdTag> page, ProdTag prodTag);

    List<ProdTag> findProdTagAllList();

    void saveProTag(ProdTag prodTag);

    void removeSoftProdTagById(Long prodTagId);

    void updateProdTag(ProdTag prodTag);
}
