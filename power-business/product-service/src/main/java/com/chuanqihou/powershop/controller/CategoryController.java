package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.domain.Category;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/27 15:31
 * @description 商品分类控制器
 */
@RestController
@RequestMapping("/prod/category")
@Api(tags = "商品分类管理接口")
public class CategoryController {

    /**
     * 商品管理业务接口
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类数据
     *
     * @return 所有分类数据
     */
    @GetMapping({"/table", "/listCategory"})
    @PreAuthorize("hasAuthority('prod:category:page')")
    @ApiOperation("获取所有分类数据")
    public Result<List<Category>> getAllCategory() {
        //获取所有分类数据
        List<Category> list = categoryService.getListCategory();
        // 返回
        return Result.success(list);
    }

    /**
     * 添加分类信息
     * @param category 分类对象
     * @return result
     */
    @PostMapping
    @ApiOperation("添加分类信息")
    @PreAuthorize("hasAuthority('prod:category:save')")
    public Result<Object> addCategory(@RequestBody Category category) {
        // 调用添加分类的业务层方法
        categoryService.saveCategory(category);
        // 返回结果
        return Result.success();
    }

    /**
     * 根据分类ID查询分类信息
     * @param categoryId 分类ID
     * @return result
     */
    @GetMapping("/info/{categoryId}")
    @ApiOperation("根据分类ID查询分类信息")
    @PreAuthorize("hasAuthority('prod:category:info')")
    public Result<Category> getCategoryInfoById(@PathVariable("categoryId") Long categoryId) {
        // 调用业务层获取分类信息
        Category category = categoryService.getById(categoryId);
        // 返回结果
        return Result.success(category);
    }

    /**
     * 修改分类信息
     * @param category 分类对象
     * @return result
     */
    @PutMapping
    @ApiOperation("修改分类信息")
    @PreAuthorize("hasAuthority('prod:category:update')")
    public Result<Object> editCategory(@RequestBody Category category) {
        //调用业务层的修改分类信息方法
        categoryService.modifyCategory(category);
        // 返回结果
        return Result.success();
    }

    /**
     * 根据分类ID删除分类信息
     * @param categoryId 分类ID
     * @return result
     */
    @DeleteMapping("/{categoryId}")
    @ApiOperation("根据分类ID删除分类信息")
    @PreAuthorize("hasAuthority('prod:category:delete')")
    public Result<Object> cutCategoryById(@PathVariable("categoryId") Long categoryId) {
        // 调用业务层的删除方法
        categoryService.removeCategoryById(categoryId);
        // 返回结果
        return Result.success();
    }

    //===========================================

    @GetMapping("/categoryInfo")
    public Result<List<Category>> getCategoryInfoByParentId(String parentId) {
        List<Category> categories = categoryService.findCategoryInfoByParentId(parentId);
        return Result.success(categories);
    }

}
