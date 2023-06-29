package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.constant.StoreConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Area;
import com.chuanqihou.powershop.mapper.AreaMapper;
import com.chuanqihou.powershop.service.AreaService;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
@CacheConfig(cacheNames = "com.chuanqihou.powershop.controller.AreaController")
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService{

    @Autowired
    private AreaMapper areaMapper;

    @Override
    @Cacheable(key = StoreConstant.AREA_LIST_CACHE_KEY)
    public List<Area> findAllAreaList() {
        return areaMapper.selectList(null);
    }

    @Override
    @Cacheable(key = StoreConstant.AREA_LIST_PID_CACHE_KEY)
    public List<Area> findAreaListByPid(Long pid) {
        return areaMapper.selectList(new LambdaQueryWrapper<Area>()
                .eq(Area::getParentId, pid)
        );
    }
}
