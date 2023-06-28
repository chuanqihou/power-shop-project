package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdPropValue;
import com.chuanqihou.powershop.domain.ProdTag;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author 传奇后
 * @date 2023/6/27 15:59
 * @description 商品分组标签接口
 */
@RestController
@RequestMapping("/prod/prodTag")
@Api(tags = "商品分组标签接口")
public class ProdTagController {

    /**
     * 商品分组标签业务层接口
     */
    @Autowired
    private ProdTagService prodTagService;

    /**
     * 分页查询标签信息
     * @param page 分页属性
     * @param prodTag 分页条件
     * @return 分页对象
     */
    @GetMapping("/page")
    @ApiOperation("分页查询标签信息")
    @PreAuthorize("hasAuthority('prod:prodTag:page')")
    public Result<Page<ProdTag>> getProdTagByPage(Page<ProdTag> page,ProdTag prodTag) {
        // 调用业务层的分页方法
        Page<ProdTag> prodTagPage = prodTagService.findProdTagByPage(page, prodTag);
        // 返回分页数据
        return Result.success(prodTagPage);
    }

    /**
     * 查询所有标签信息
     * @return 标签信息
     */
    @GetMapping("/listTagList")
    @ApiOperation("查询所有标签信息")
    public Result<List<ProdTag>> getAllProdTags() {
         // 调用业务层的查询方法
        List<ProdTag> prodTags = prodTagService.findProdTagAllList();
        // 返回数据
        return Result.success(prodTags);
    }

    /**
     * 新增标签信息
     * @param prodTag 标签对象
     * @return result
     */
    @PostMapping
    @ApiOperation("新增标签信息")
    @PreAuthorize("hasAuthority('prod:prodTag:save')")
    public Result<Object> addProdTag(@RequestBody ProdTag prodTag) {
        // 调用业务层的新增方法
        prodTagService.saveProTag(prodTag);
        // 返回数据
        return Result.success();
    }

    /**
     * 根据标签ID查询标签信息
     *
     * @param prodTagId 标签ID
     * @return 标签信息
     */
    @GetMapping("/info/{prodTagId}")
    @ApiOperation("根据标签ID查询标签信息")
    @PreAuthorize("hasAuthority('prod:prodTag:info')")
    public Result<ProdTag> getProdTagInfoById(@PathVariable("prodTagId") Long prodTagId) {
        // 调用业务层的查询方法
        ProdTag prodTag = prodTagService.getById(prodTagId);
        // 返回标签信息
        return Result.success(prodTag);
    }

    /**
     * 修改标签信息
     * @param prodTag 标签对象
     * @return result
     */
    @PutMapping
    @ApiOperation("修改标签信息")
    @PreAuthorize("hasAuthority('prod:prodTag:update')")
    public Result<Object> editProdTage(@RequestBody ProdTag prodTag) {
        // 调用业务层的修改方法
        prodTagService.updateProdTag(prodTag);
        // 返回数据
        return Result.success();
    }

    /**
     * 根据标签ID删除标签信息
     *
     * @param prodTagId 标签ID
     * @return result
     */
    @DeleteMapping("/{prodTagId}")
    @ApiOperation("根据标签ID删除标签信息")
    @PreAuthorize("hasAuthority('prod:prodTag:delete')")
    public Result<Object> cutProdTagById(@PathVariable("prodTagId") Long prodTagId) {
        // 调用业务层的删除方法
        prodTagService.removeSoftProdTagById(prodTagId);
        // 返回数据
        return Result.success();
    }


}
