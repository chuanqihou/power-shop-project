package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdTag;
import com.chuanqihou.powershop.model.ProdEs;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.SearchProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/6/30 19:01
 * @description
 */
@RestController
@RequestMapping("/prod")
public class SearchProdController {

    @Autowired
    private SearchProdService searchProdService;

    @GetMapping("/prodListByTagId")
    public Result<Page<ProdEs>> getProdTageById(Long tagId,Long size) {
        Page<ProdEs> prodEsPage = searchProdService.searchProdEsByTagIdPage(tagId,size);
        return Result.success(prodEsPage);
    }

}
