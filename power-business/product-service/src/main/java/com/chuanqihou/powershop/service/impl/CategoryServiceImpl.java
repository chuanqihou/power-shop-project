package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.constant.ProductConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Category;
import com.chuanqihou.powershop.mapper.CategoryMapper;
import com.chuanqihou.powershop.service.CategoryService;
import org.springframework.util.ObjectUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public boolean saveCategory(Category category) {
        category.setCreateTime(new Date());
        if (ObjectUtils.isEmpty(category.getParentId())) {
            category.setParentId(ProductConstant.CATEGORY_ROOT_ID);
        }
        return categoryMapper.insert(category)==1;
    }

    @Override
    public List<Category> getListCategory() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>().orderByDesc(Category::getSeq));
    }
}
