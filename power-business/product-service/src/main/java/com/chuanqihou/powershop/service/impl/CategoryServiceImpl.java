package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.domain.Category;
import com.chuanqihou.powershop.domain.Prod;
import com.chuanqihou.powershop.ex.ProductServiceException;
import com.chuanqihou.powershop.mapper.CategoryMapper;
import com.chuanqihou.powershop.service.CategoryService;
import com.chuanqihou.powershop.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProdService prodService;

    @Override
    public void saveCategory(Category category) {
        category.setCreateTime(new Date());
        if (ObjectUtils.isEmpty(category.getParentId())) {
            category.setParentId(ProductConstant.CATEGORY_ROOT_ID);
        }
        int insertCategory = categoryMapper.insert(category);
        if (insertCategory != 1) {
            throw new ProductServiceException("添加商品分类失败！");
        }
    }

    @Override
    public List<Category> getListCategory() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>().orderByDesc(Category::getSeq));
    }

    @Override
    public void modifyCategory(Category category) {

        category.setUpdateTime(new Date());

        int updateCategoryResult = categoryMapper.updateById(category);

        if (updateCategoryResult != 1) {
            throw new ProductServiceException("更新分类失败，请联系管理员！");
        }

    }

    @Override
    public void removeCategoryById(Long categoryId) {
        long num = prodService.count(new LambdaQueryWrapper<Prod>()
                .eq(Prod::getCategoryId, categoryId)
        );
        if (num > 0) {
            throw new ProductServiceException("该分类下存在关联的商品，请先删除该分类下的商品信息后重试！");
        }

        int deleteCategoryResult = categoryMapper.deleteById(categoryId);
        if (deleteCategoryResult != 1) {
            throw new ProductServiceException("删除分类失败，请联系管理员！");
        }
    }

    @Override
    public List<Category> findCategoryInfoByParentId(String parentId) {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(StringUtils.hasText(parentId), Category::getParentId, parentId)
                .eq(Category::getStatus, 1)
                .orderByDesc(Category::getSeq)
        );
    }

}
