package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.model.ProdEs;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.SearchProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/7/3 19:39
 * @description
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchProdService searchProdService;

    @GetMapping("/searchProdPage")
    public Result<Page<ProdEs>> searchProdPage(Page<ProdEs> page, String prodName, Long sort) {
        Page<ProdEs> prodEsPage = searchProdService.searchProdPage(page, prodName, sort);
        return Result.success(page);
    }

}
