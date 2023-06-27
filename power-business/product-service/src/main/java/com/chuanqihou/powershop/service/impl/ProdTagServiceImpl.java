package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.model.Result;
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
        ProdTag prodTag = prodTagMapper.selectById(prodTagId);
        prodTag.setUpdateTime(new Date());
        prodTag.setStatus(false);
        prodTagMapper.updateById(prodTag);
    }

    @Override
    public void updateProdTag(ProdTag prodTag) {
        prodTag.setUpdateTime(new Date());
        prodTagMapper.updateById(prodTag);
    }

}
