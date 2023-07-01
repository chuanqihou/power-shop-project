package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.model.ProdEs;

/**
 * @author 传奇后
 * @date 2023/7/1 9:28
 * @description
 */
public interface SearchProdService {
    Page<ProdEs> searchProdEsByTagIdPage(Long tagId, Long size);
}
