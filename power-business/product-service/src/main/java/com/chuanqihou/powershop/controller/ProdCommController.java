package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdComm;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 传奇后
 * @date 2023/6/27 15:13
 * @description
 */
@RestController
@RequestMapping("/prod/prodComm")
public class ProdCommController {

    @Autowired
    private ProdCommService prodCommService;

    @GetMapping("/page")
    public Result<Page<ProdComm>> getProdCommPage(Page<ProdComm> page,ProdComm prodComm) {

        Page<ProdComm> prodCommPage = prodCommService.findProCommPage(page, prodComm);

        return Result.success(prodCommPage);
    }

    @GetMapping("/{prodCommId}")
    public Result<ProdComm> getProdCommInfoById(@PathVariable("prodCommId") Long prodCommId) {

        ProdComm prodComm = prodCommService.getById(prodCommId);

        return Result.success(prodComm);
    }

    @PutMapping
    public Result<Object> editProdComm(@RequestBody ProdComm prodComm) {
        prodCommService.modifyProdComm(prodComm);
        return Result.success();
    }

}
