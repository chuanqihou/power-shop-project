package com.chuanqihou.powershop.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.domain.*;
import com.chuanqihou.powershop.dto.ProdDTO;
import com.chuanqihou.powershop.ex.ProductServiceException;
import com.chuanqihou.powershop.mapper.ProdCommMapper;
import com.chuanqihou.powershop.mapper.ProdTagReferenceMapper;
import com.chuanqihou.powershop.model.ProdEs;
import com.chuanqihou.powershop.service.ProdTagReferenceService;
import com.chuanqihou.powershop.service.SkuService;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProdTagReferenceMapper prodTagReferenceMapper;

    @Autowired
    private ProdCommMapper prodCommMapper;


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

//=============================================================================================

    /**
     * 统计商品更新时间在一定时间段内商品数量
     * 当t1、t2都为null时，统计表中所有的商品数量
     * @param t1 统计起始时间
     * @param t2 统计结束时间
     * @return 该短时间内的商品总数
     */
    @Override
    public int selectProdCount(Date t1, Date t2) {
        Long count = prodMapper.selectCount(new LambdaQueryWrapper<Prod>()
                .eq(Prod::getStatus, 1)
                .between(!ObjectUtils.isEmpty(t1) && !ObjectUtils.isEmpty(t2), Prod::getUpdateTime, t1, t2)
        );
        return count.intValue();
    }

    /**
     * 分页查询商品信息，并封装到ProdEs中
     * 当t1、t2都为null时，分页查询表中所有的商品信息
     * 否则分页查询 商品更新时间在t1到t2时间段内的商品信息
     * @param currentPage 当前页码
     * @param size 每页显示条数
     * @param t1 分页条件：统计商品更新起始时间
     * @param t2 分页条件：统计商品更新结束时间
     * @return 集合(proEs对象)
     */
    @Override
    public List<ProdEs> selectProdEsByPage(int currentPage, int size, Date t1, Date t2) {
        // 分页查询商品信息
        Page<Prod> page = new Page<>(currentPage, size, false);
        Page<Prod> prodPage = prodMapper.selectPage(page, new LambdaQueryWrapper<Prod>()
                .eq(Prod::getStatus, 1)
                .between(!ObjectUtils.isEmpty(t1) && !ObjectUtils.isEmpty(t2), Prod::getUpdateTime, t1, t2)
        );
        // 获取prod集合
        List<Prod> records = prodPage.getRecords();
        // 获取prod对象的id集合
        List<Long> prodIdList = records.stream().map(Prod::getProdId).collect(Collectors.toList());
        // 通过商品id查询对应的商品标签信息
        List<ProdTagReference> prodTagReferenceList = prodTagReferenceMapper.selectList(new LambdaQueryWrapper<ProdTagReference>()
                .in(ProdTagReference::getProdId, prodIdList)
        );
        // 通过商品id查询对应的评论信息
        List<ProdComm> prodCommList = prodCommMapper.selectList(new LambdaQueryWrapper<ProdComm>()
                .in(ProdComm::getProdId, prodIdList)
        );
        // 定义一个寻存储ProEs的集合
        List<ProdEs> prodEsList = new ArrayList<>();
        // 循环遍历prod集合，将prod对象转换成prodEs对象，并设置prodEs对象中的三个属性
        records.forEach(prod -> {
            // 拷贝属性
            ProdEs prodEs = new ProdEs();
            BeanUtils.copyProperties(prod, prodEs);
            // 获取商品对应的商品标签id
            List<Long> prodTagIdList = prodTagReferenceList.stream().filter(ptr -> ptr.getProdId().equals(prodEs.getProdId())).map(ProdTagReference::getTagId).collect(Collectors.toList());
            // 设置商品标签
            prodEs.setTagList(prodTagIdList);
            // 获取并设置商品的好评数和好评率
            long prodCommTotal = 0;
            long favorableCommentNum = 0;
            // 循环遍历所有评论
            for (ProdComm prodComm : prodCommList) {
                // 找到该商品所对应的评论
                if (prodComm.getProdId().equals(prodEs.getProdId())) {
                    // 商品评论数 +1
                    prodCommTotal++;
                    if (prodComm.getEvaluate().equals(0)) {
                        // 如果评论是好评，则好评数 +1
                        favorableCommentNum++;
                    }
                }
            }
            if (prodCommTotal != 0) {
                // 设置商品好评数
                prodEs.setPraiseNumber(favorableCommentNum);
                // 计算商品的好评率
                BigDecimal positiveRating = new BigDecimal(favorableCommentNum).divide(new BigDecimal(prodCommTotal), 2, RoundingMode.UP).multiply(new BigDecimal(100));
                // 设置商品的好评率
                prodEs.setPositiveRating(positiveRating);
            }else {
                // 设置商品好评数
                prodEs.setPraiseNumber(0L);
                // 设置商品的好评率
                prodEs.setPositiveRating(new BigDecimal(0));
            }
            //将prodEs对象添加到list集合中
            prodEsList.add(prodEs);
        });
        // 返回数据
        return prodEsList;
    }
}
