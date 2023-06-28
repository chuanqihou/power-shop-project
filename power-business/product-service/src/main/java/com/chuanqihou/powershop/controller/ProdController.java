package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.Prod;
import com.chuanqihou.powershop.dto.ProdDTO;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 传奇后
 * @date 2023/6/27 19:23
 * @description 商品管理接口
 */
@RestController
@RequestMapping("/prod/prod")
@Api(tags = "商品管理接口")
public class ProdController {

    /**
     * 商品管理业务层接口
     */
    @Autowired
    private ProdService prodService;

    /**
     * 分页查询商品信息
     * @param page 分页属性
     * @param prod 分页条件
     * @return 返回分页对象
     */
    @GetMapping("/page")
    @ApiOperation("分页查询商品信息")
    @PreAuthorize("hasAuthority('prod:prod:page')")
    public Result<Page<Prod>> getProdByPage(Page<Prod> page,Prod prod) {
        // 调用业务层的商品分页方法
        Page<Prod> prodPage = prodService.findProdByPage(page, prod);
        // 返回分页数据
        return Result.success(prodPage);
    }

    /**
     * 新增商品信息
     * @param prodDTO 商品信息DTO对象
     * @return result
     */
    @PostMapping
    @ApiOperation("新增商品信息")
    @PreAuthorize("hasAuthority('prod:prod:save')")
    public Result<Object> addProd(@RequestBody ProdDTO prodDTO) {
        // 调用业务层的保存商品信息方法
        prodService.saveProd(prodDTO);
        // 返回数据
        return Result.success();
    }

    /**
     * 根据商品ID查询商品信息
     *
     * @param prodId 商品ID
     * @return 商品信息
     */
    @GetMapping("/info/{prodId}")
    @ApiOperation("根据商品ID查询商品信息")
    @PreAuthorize("hasAuthority('prod:prod:info')")
    public Result<ProdDTO> getProdInfoByProdId(@PathVariable("prodId") Long prodId) {
        // 调用业务层获取商品信息的方法
        ProdDTO prodDTO = prodService.getProdById(prodId);
        // 返回数据
        return Result.success(prodDTO);
    }

    /**
     * 修改商品信息
     * @param prodDTO 商品DTO对象
     * @return result
     */
    @PutMapping
    @ApiOperation("修改商品信息")
    @PreAuthorize("hasAuthority('prod:prod:update')")
    public Result<Object> editProd(@RequestBody ProdDTO prodDTO) {
        // 调用业务层的修改商品信息方法
        prodService.modifyProd(prodDTO);
        // 返回数据
        return Result.success();
    }

    /**
     * 根据商品ID删除商品信息
     * @param prodId 商品ID
     * @return result
     */
    @DeleteMapping("/{prodId}")
    @ApiOperation("根据商品ID删除商品信息")
    @PreAuthorize("hasAuthority('prod:prod:delete')")
    public Result<Object> cutProdByProdId(@PathVariable("prodId") Long prodId) {
        // 调用业务层的删除商品方法
        prodService.removeProdBuProdId(prodId);
        // 返回数据
        return Result.success();
    }

}
