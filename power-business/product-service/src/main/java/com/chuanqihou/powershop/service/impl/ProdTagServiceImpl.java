package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.domain.ProdTagReference;
import com.chuanqihou.powershop.ex.ProductServiceException;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdTagReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.mapper.ProdTagMapper;
import com.chuanqihou.powershop.domain.ProdTag;
import com.chuanqihou.powershop.service.ProdTagService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class ProdTagServiceImpl extends ServiceImpl<ProdTagMapper, ProdTag> implements ProdTagService{

    @Autowired
    private ProdTagMapper prodTagMapper;

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    @Override
    public Page<ProdTag> findProdTagByPage(Page<ProdTag> page, ProdTag prodTag) {

        return prodTagMapper.selectPage(page, new LambdaQueryWrapper<ProdTag>()
                .like(StringUtils.hasText(prodTag.getTitle()),ProdTag::getTitle, prodTag.getTitle())
                .eq(!ObjectUtils.isEmpty(prodTag.getStatus()),ProdTag::getStatus, prodTag.getStatus())
                .orderByDesc(ProdTag::getSeq)
        );
    }

    @Override
    public List<ProdTag> findProdTagAllList() {
        return prodTagMapper.selectList(new LambdaQueryWrapper<ProdTag>()
                .eq(ProdTag::getStatus, ProductConstant.PROD_TAG_NORMAL_STATUS)
        );
    }

    @Override
    public void saveProTag(ProdTag prodTag) {
        prodTag.setCreateTime(new Date());
        prodTag.setUpdateTime(new Date());
        prodTagMapper.insert(prodTag);
    }

    @Override
    public void removeSoftProdTagById(Long prodTagId) {
        long num = prodTagReferenceService.count(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getTagId, prodTagId)
        );
        if (num > 0) {
            throw new ProductServiceException("该分组下存在商品，请先删除该分组下所有商品再重试！");
        }
        // 删除（软删除）
        ProdTag prodTag = prodTagMapper.selectById(prodTagId);
        prodTag.setUpdateTime(new Date());
        prodTag.setStatus(false);
        int updateProdTagResult = prodTagMapper.updateById(prodTag);
        if (updateProdTagResult != 1) {
            throw new ProductServiceException("删除分组失败，请联系管理员！");
        }
    }

    @Override
    public void updateProdTag(ProdTag prodTag) {
        prodTag.setUpdateTime(new Date());
        prodTagMapper.updateById(prodTag);
    }

}
