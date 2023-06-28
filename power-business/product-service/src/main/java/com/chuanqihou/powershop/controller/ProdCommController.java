package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdComm;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdCommService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 传奇后
 * @date 2023/6/27 15:13
 * @description 商品评论管理接口
 */
@RestController
@RequestMapping("/prod/prodComm")
@Api(tags = "商品评论管理接口")
public class ProdCommController {

    /**
     * 商品评论业务层接口
     */
    @Autowired
    private ProdCommService prodCommService;

    /**
     * 分页查询商品评论信息
     * @param page 分页属性
     * @param prodComm 分页条件
     * @return 分页对象
     */
    @GetMapping("/page")
    @ApiOperation("分页查询商品评论信息")
    @PreAuthorize("hasAuthority('prod:prodComm:page')")
    public Result<Page<ProdComm>> getProdCommPage(Page<ProdComm> page,ProdComm prodComm) {
        // 调用业务层分页方法
        Page<ProdComm> prodCommPage = prodCommService.findProCommPage(page, prodComm);
        // 返回结果
        return Result.success(prodCommPage);
    }

    /**
     * 根据商品评论ID查询评论信息
     * @param prodCommId 商品评论ID
     * @return result
     */
    @GetMapping("/{prodCommId}")
    @ApiOperation("根据商品评论ID查询评论信息")
    @PreAuthorize("hasAuthority('prod:prodComm:info')")
    public Result<ProdComm> getProdCommInfoById(@PathVariable("prodCommId") Long prodCommId) {

        ProdComm prodComm = prodCommService.getById(prodCommId);

        return Result.success(prodComm);
    }

    /**
     * 更改商品评论状态 （回复、审核等）
     * @param prodComm 商品评论对象
     * @return result
     */
    @PutMapping
    @ApiOperation("更改商品评论状态 （回复、审核等）")
    @PreAuthorize("hasAuthority('prod:prodComm:update')")
    public Result<Object> editProdComm(@RequestBody ProdComm prodComm) {
        // 调用商品评论业务层中的修改方法
        prodCommService.modifyProdComm(prodComm);
        // 返回数据
        return Result.success();
    }

}
