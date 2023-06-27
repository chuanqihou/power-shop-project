package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.Prod;
import com.chuanqihou.powershop.dto.ProdDTO;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 传奇后
 * @date 2023/6/27 19:23
 * @description
 */
@RestController
@RequestMapping("/prod/prod")
public class ProdController {

    @Autowired
    private ProdService prodService;

    @GetMapping("/page")
    public Result<Page<Prod>> getProdByPage(Page<Prod> page,Prod prod) {
        Page<Prod> prodPage = prodService.findProdByPage(page, prod);
        return Result.success(prodPage);
    }

    @PostMapping
    public Result<Object> addProd(@RequestBody ProdDTO prodDTO) {
        boolean result = prodService.saveProd(prodDTO);
        return result ? Result.success() : Result.fails(-1, "添加商品失败！");
    }

}
