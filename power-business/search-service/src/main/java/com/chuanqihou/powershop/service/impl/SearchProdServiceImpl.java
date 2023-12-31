package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.model.ProdEs;
import com.chuanqihou.powershop.service.SearchProdService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<ProdEs> searchProdEsByTagIdPage(Long tagId, Integer size) {

        //设置查询条件
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("tagList", tagId);

        //封装
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(1, size))
                .build();

        //调用查询
        SearchHits<ProdEs> search = esTemplate.search(nativeSearchQuery, ProdEs.class);

        // 获取数据
        List<ProdEs> collect = search.stream().map(SearchHit::getContent).collect(Collectors.toList());

        //封装数据到page中
        Page<ProdEs> prodEsPage = new Page<>();
        prodEsPage.setRecords(collect);
        prodEsPage.setTotal(search.getTotalHits());
        // 返回数据
        return prodEsPage;
    }

    @Override
    public Page<ProdEs> searchProdByCategoryId(Long cateGoryId) {
        // 设置查询类型
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("categoryId", cateGoryId);
        // 封装查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        // 调用查询
        SearchHits<ProdEs> search = esTemplate.search(nativeSearchQuery, ProdEs.class);
        // 从结果中获取数据
        List<ProdEs> prodEsList = search.stream().map(SearchHit::getContent).collect(Collectors.toList());

        // 将数据封装成Page<ProdEs>
        Page<ProdEs> prodEsPage = new Page<>();
        prodEsPage.setRecords(prodEsList);
        prodEsPage.setTotal(search.getTotalHits());
        // 返回数据

        return prodEsPage;
    }

    @Override
    public Page<ProdEs> searchProdPage(Page<ProdEs> page, String prodName, Long sort) {

        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("prodName", prodName);

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<ProdEs> search = esTemplate.search(nativeSearchQuery, ProdEs.class);

        List<ProdEs> prodEsList = search.stream().map(SearchHit::getContent).collect(Collectors.toList());

        page.setRecords(prodEsList);
        page.setTotal(search.getTotalHits());

        return page;
    }
}
