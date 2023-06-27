package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.domain.Category;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 传奇后
 * @date 2023/6/27 15:31
 * @description
 */
@RestController
@RequestMapping("/prod/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/table","/listCategory"})
    public Result<List<Category>> getAllCategory() {
        List<Category> list = categoryService.getListCategory();
        return Result.success(list);
    }

    @PostMapping
    public Result<Object> addCategory(@RequestBody Category category) {
        boolean saveCategoryResult = categoryService.saveCategory(category);
        return saveCategoryResult ? Result.success() : Result.fails(-1, "新增分类失败！");
    }

}
