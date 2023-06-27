package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdProp;
import com.chuanqihou.powershop.domain.ProdPropValue;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdPropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/27 18:36
 * @description
 */
@RestController
@RequestMapping("/prod/spec")
public class ProdPropController {

    @Autowired
    private ProdPropService prodPropService;

    @GetMapping("/page")
    public Result<Page<ProdProp>> getProdPropAndValueByPage(Page<ProdProp> page, ProdProp prodProp) {

        Page<ProdProp> prodPropPage = prodPropService.findProdPropAndValueByPage(page, prodProp);

        return Result.success(prodPropPage);
    }

    @GetMapping("/list")
    public Result<List<ProdProp>> getProdPropAll() {

        List<ProdProp> prodPropList = prodPropService.findProdPropAll();

        return Result.success(prodPropList);
    }

    @GetMapping("/listSpecValue/{propId}")
    public Result<List<ProdPropValue>> getAllProdPropValue(@PathVariable("propId") Long prodId) {
        List<ProdPropValue> prodPropValueList = prodPropService.findProdPropValueByPropId(prodId);
        return Result.success(prodPropValueList);
    }

}
