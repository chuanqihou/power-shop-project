package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.model.ProdEs;
import com.chuanqihou.powershop.service.SearchProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 传奇后
 * @date 2023/7/1 9:29
 * @description
 */
@Service
public class SearchProdServiceImpl implements SearchProdService {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Override
    public Page<ProdEs> searchProdEsByTagIdPage(Long tagId, Long size) {

        //设置查询条件

        //封装

        //调用查询
        //esTemplate.search();

        // 获取数据

        //封装数据到page中

        // 返回数据
        return null;
    }
}
