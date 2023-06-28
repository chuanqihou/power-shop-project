package com.chuanqihou.powershop.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.domain.*;
import com.chuanqihou.powershop.dto.ProdDTO;
import com.chuanqihou.powershop.ex.ProductServiceException;
import com.chuanqihou.powershop.service.ProdTagReferenceService;
import com.chuanqihou.powershop.service.SkuService;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.mapper.ProdMapper;
import com.chuanqihou.powershop.service.ProdService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description 商品管理业务实现类
 */
@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService{

    /**
     *
     */
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
                .in(Prod::getStatus, 0, 1)
                .orderByDesc(Prod::getCreateTime)
        );
    }
    @Override
    @Transactional
    public void saveProd(ProdDTO prodDTO) {
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
            throw new ProductServiceException("商品添加失败，请联系管理员！");
        }
    }

    @Override
    public ProdDTO getProdById(Long prodId) {
        //根据商品id查询商品表信息，并将属性拷贝到prodDto对象
        ProdDTO prodDTO = new ProdDTO();
        Prod prod = prodMapper.selectById(prodId);
        BeanUtils.copyProperties(prod, prodDTO);

        prodDTO.setDeliveryModeVo(JSON.parseObject(prod.getDeliveryMode(), ProdDTO.DeliveryMode.class));

        //根据商品id查询该商品的所有商品标签
        List<ProdTagReference> prodTagReferenceList = prodTagReferenceService.list(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, prodId)
                .eq(ProdTagReference::getStatus, ProductConstant.PROD_TAG_NORMAL_STATUS)
        );
        List<Long> prodTageIdList = prodTagReferenceList.stream().map(ProdTagReference::getTagId).collect(Collectors.toList());
        //设置到prodDto中
        prodDTO.setTagList(prodTageIdList);

        //根据商品id查询该商品的所有sku信息
        List<Sku> skuList = skuService.list(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId)
                .eq(Sku::getStatus, 1)
        );
        prodDTO.setSkuList(skuList);

        return prodDTO;
    }

    @Override
    @Transactional
    public void modifyProd(ProdDTO prodDTO) {

        // 更新商品表信息
        prodDTO.setDeliveryMode(JSON.toJSONString(prodDTO.getDeliveryModeVo()));
        int updateProd = prodMapper.updateById(prodDTO);

        // 更新商品标签信息
        //删除原来的商品标签信息(软删除)
        boolean deleteProdTagReferByProdId = prodTagReferenceService.update(new LambdaUpdateWrapper<ProdTagReference>()
                .set(ProdTagReference::getStatus, 0)
                .eq(ProdTagReference::getProdId, prodDTO.getProdId())
                .eq(ProdTagReference::getStatus, 1)
        );
        //重新添加商品标签信息
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

        // 更新商品sku信息
        // 删除原来的sku信息
        skuService.remove(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodDTO.getProdId())
        );
        //重新添加新的sku信息
        List<Sku> skuList = prodDTO.getSkuList();
        skuList.forEach(sku -> {
            sku.setProdId(prodDTO.getProdId());
            sku.setCreateTime(new Date());
            sku.setUpdateTime(new Date());
        });
        boolean saveBatchSku = skuService.saveBatch(skuList);

        if (!(updateProd == 1 && deleteProdTagReferByProdId && saveBatchProdTagRefer && saveBatchSku)) {
            throw new ProductServiceException("更新商品信息失败，请联系管理员！");
        }

    }

    @Override
    public void removeProdBuProdId(Long prodId) {

        //将商品表中的status字段修改成-1（软删除）
        Prod prod = prodMapper.selectById(prodId);
        prod.setStatus(-1);
        int softDeleteProdByProdId = prodMapper.updateById(prod);

        //商品与商品标签表的字段status修改成0（软删除）
        boolean softDelProdReferByProdId = prodTagReferenceService.update(new LambdaUpdateWrapper<ProdTagReference>()
                .set(ProdTagReference::getStatus, 0)
                .eq(ProdTagReference::getProdId, prodId)
                .eq(ProdTagReference::getStatus, 1)
        );

        //商品信息对应的sku信息（硬删除）
        boolean removeSkuByProdId = skuService.remove(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId)
        );

        if (!(softDeleteProdByProdId == 1 && softDelProdReferByProdId && removeSkuByProdId)) {
            throw new ProductServiceException("删除商品信息失败，请联系管理员！");
        }

    }
}
