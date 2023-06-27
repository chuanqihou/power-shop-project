package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdPropValue;
import com.chuanqihou.powershop.domain.ProdTag;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author 传奇后
 * @date 2023/6/27 15:59
 * @description
 */
@RestController
@RequestMapping("/prod/prodTag")
public class ProdTagController {

    @Autowired
    private ProdTagService prodTagService;

    @GetMapping("/page")
    public Result<Page<ProdTag>> getProdTagByPage(Page<ProdTag> page,ProdTag prodTag) {

        Page<ProdTag> prodTagPage = prodTagService.findProdTagByPage(page, prodTag);

        return Result.success(prodTagPage);
    }

    @GetMapping("/listTagList")
    public Result<List<ProdTag>> getAllProdTags() {
         List<ProdTag> prodTags = prodTagService.findProdTagAllList();
         return Result.success(prodTags);
    }

    @PostMapping
    public Result<Object> addProdTag(@RequestBody ProdTag prodTag) {
        prodTagService.saveProTag(prodTag);
        return Result.success();
    }

    @GetMapping("/info/{prodTagId}")
    public Result<ProdTag> getProdTagInfoById(@PathVariable("prodTagId") Long prodTagId) {
        ProdTag prodTag = prodTagService.getById(prodTagId);
        return Result.success(prodTag);
    }

    @PutMapping
    public Result<Object> editProdTage(@RequestBody ProdTag prodTag) {
        prodTagService.updateProdTag(prodTag);
        return Result.success();
    }

    @DeleteMapping("/{prodTagId}")
    public Result<Object> cutProdTagById(@PathVariable("prodTagId") Long prodTagId) {
        prodTagService.removeSoftProdTagById(prodTagId);
        return Result.success();
    }


}
