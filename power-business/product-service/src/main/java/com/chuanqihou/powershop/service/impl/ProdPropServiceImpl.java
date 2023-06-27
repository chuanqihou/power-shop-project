package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.domain.ProdPropValue;
import com.chuanqihou.powershop.service.ProdPropValueService;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.mapper.ProdPropMapper;
import com.chuanqihou.powershop.domain.ProdProp;
import com.chuanqihou.powershop.service.ProdPropService;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class ProdPropServiceImpl extends ServiceImpl<ProdPropMapper, ProdProp> implements ProdPropService{

    @Autowired
    private ProdPropMapper prodPropMapper;

    @Autowired
    private ProdPropValueService prodPropValueService;

    @Override
    public Page<ProdProp> findProdPropAndValueByPage(Page<ProdProp> page, ProdProp prodProp) {

        // 分页查询属性（List<ProdProp>）
        Page<ProdProp> prodPropPage = prodPropMapper.selectPage(page, new LambdaQueryWrapper<ProdProp>()
                .eq(!AuthUtil.getShopId().equals(ProductConstant.ADMIN_SHOP_ID), ProdProp::getShopId, AuthUtil.getShopId())
                .like(StringUtils.hasText(prodProp.getPropName()), ProdProp::getPropName, prodProp.getPropName())
        );

        // 设置属性对应的属性值
        List<ProdProp> prodPropList = prodPropPage.getRecords();

        // 第一种方法（一个一个查询添加）【不推荐，需要多次查询数据库】
/*        prodPropList.forEach(prodPropEach -> {
            List<ProdPropValue> prodPropValueList = prodPropValueService.list(new LambdaQueryWrapper<ProdPropValue>()
                    .eq(ProdPropValue::getPropId, prodPropEach.getPropId())
            );
            prodPropEach.setProdPropValues(prodPropValueList);
        });*/

        // 第二种方法（使用stream流中的分组）

/*        //获取propId集合
        List<Long> propIds = prodPropList.stream().map(ProdProp::getPropId).collect(Collectors.toList());
        // 批量查询属性值
        List<ProdPropValue> prodPropValueList = prodPropValueService.list(new LambdaQueryWrapper<ProdPropValue>().in(ProdPropValue::getPropId, propIds));
        // 根据propId进行分组
        Map<Long, List<ProdPropValue>> prodPropValueGroupByPropId = prodPropValueList.stream().collect(Collectors.groupingBy(ProdPropValue::getPropId));
        prodPropValueGroupByPropId.forEach((propId,prodPropValues) ->{
            prodPropList.forEach(prodPropEach ->{
                if (prodPropEach.getPropId().equals(propId)) {
                    prodPropEach.setProdPropValues(prodPropValues);
                }
            });
        });*/

        // 第三种方法（推荐，只查询一次数据库，java代码稍微麻烦一点）

        //获取propId集合
        List<Long> propIds = prodPropList.stream().map(ProdProp::getPropId).collect(Collectors.toList());
        // 批量查询属性值
        List<ProdPropValue> prodPropValueList = prodPropValueService.list(new LambdaQueryWrapper<ProdPropValue>().in(ProdPropValue::getPropId, propIds));
        // 遍历属性集合
        prodPropList.forEach(prodPropEach ->{
            // 根据proId过滤，找出属性所对应的所有属性值
            List<ProdPropValue> prodPropValueListFilterByProdId = prodPropValueList.stream().filter(prodPropValue -> prodPropEach.getPropId().equals(prodPropValue.getPropId())).collect(Collectors.toList());
            //给属性添加对应的属性值
            prodPropEach.setProdPropValues(prodPropValueListFilterByProdId);
        });



        return prodPropPage;
    }

    @Override
    public List<ProdProp> findProdPropAll() {
        return prodPropMapper.selectList(null);
    }

    @Override
    public List<ProdPropValue> findProdPropValueByPropId(Long prodId) {
        return prodPropValueService.list(new LambdaQueryWrapper<ProdPropValue>()
                .eq(ProdPropValue::getPropId, prodId)
        );
    }
}
