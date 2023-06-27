package com.chuanqihou.powershop.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.domain.ProdTagReference;
import com.chuanqihou.powershop.domain.Sku;
import com.chuanqihou.powershop.dto.ProdDTO;
import com.chuanqihou.powershop.service.ProdTagReferenceService;
import com.chuanqihou.powershop.service.SkuService;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Prod;
import com.chuanqihou.powershop.mapper.ProdMapper;
import com.chuanqihou.powershop.service.ProdService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService{

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    @Autowired
    private SkuService skuService;

    @Override
    public Page<Prod> findProdByPage(Page<Prod> page, Prod prod) {

        return prodMapper.selectPage(page, new LambdaQueryWrapper<Prod>()
                .eq(!AuthUtil.getShopId().equals(ProductConstant.ADMIN_SHOP_ID), Prod::getShopId, AuthUtil.getShopId())
                .like(StringUtils.hasText(prod.getProdName()), Prod::getProdName, prod.getProdName())
                .eq(!ObjectUtils.isEmpty(prod.getStatus()), Prod::getStatus, prod.getStatus())
        );
    }

    @Override
    @Transactional
    public boolean saveProd(ProdDTO prodDTO) {
        //设置商品信息
        prodDTO.setShopId(AuthUtil.getShopId());
        prodDTO.setSoldNum(0);
        prodDTO.setCreateTime(new Date());
        prodDTO.setUpdateTime(new Date());
        prodDTO.setPutawayTime(new Date());
        prodDTO.setDeliveryMode(JSON.toJSONString(prodDTO.getDeliveryModeVo()));
        //保存商品信息
        int insertProd = prodMapper.insert(prodDTO);

        //商品和商品标签表中添加数据
        List<Long> tagList = prodDTO.getTagList();
        List<ProdTagReference> prodTagReferences = new ArrayList<>();
        tagList.forEach(tagId -> {
            ProdTagReference prodTagReference = new ProdTagReference()
                    .setProdId(prodDTO.getProdId())
                    .setTagId(tagId)
                    .setStatus(true)
                    .setCreateTime(new Date())
                    .setShopId(AuthUtil.getShopId());
            prodTagReferences.add(prodTagReference);
        });
        boolean saveBatchProdTagRefer = prodTagReferenceService.saveBatch(prodTagReferences);

        //添加数据到sku表中
        List<Sku> skuList = prodDTO.getSkuList();
        skuList.forEach(sku -> {
            sku.setProdId(prodDTO.getProdId());
            sku.setCreateTime(new Date());
            sku.setUpdateTime(new Date());
        });
        boolean saveBatchSku = skuService.saveBatch(skuList);

        boolean result = insertProd==1 && saveBatchProdTagRefer && saveBatchSku;
        if (!result) {
            throw new RuntimeException("商品添加失败，请联系管理员！");
        }

        return result;
    }
}
