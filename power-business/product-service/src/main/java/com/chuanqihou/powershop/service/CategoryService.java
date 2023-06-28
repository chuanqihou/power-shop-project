package com.chuanqihou.powershop.service;

import com.chuanqihou.powershop.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface CategoryService extends IService<Category>{


    void saveCategory(Category category);

    List<Category> getListCategory();

    void modifyCategory(Category category);

    void removeCategoryById(Long categoryId);
}
