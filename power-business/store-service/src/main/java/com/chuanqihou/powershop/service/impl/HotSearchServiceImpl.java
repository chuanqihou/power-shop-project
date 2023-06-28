package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.mapper.HotSearchMapper;
import com.chuanqihou.powershop.domain.HotSearch;
import com.chuanqihou.powershop.service.HotSearchService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class HotSearchServiceImpl extends ServiceImpl<HotSearchMapper, HotSearch> implements HotSearchService{

    @Autowired
    private HotSearchMapper hotSearchMapper;

    @Override
    public Page<HotSearch> findHotSearchByPage(Page<HotSearch> page, HotSearch hotSearch) {

        return hotSearchMapper.selectPage(page, new LambdaQueryWrapper<HotSearch>()
                .like(StringUtils.hasText(hotSearch.getTitle()), HotSearch::getTitle, hotSearch.getTitle())
                .like(StringUtils.hasText(hotSearch.getContent()), HotSearch::getContent, hotSearch.getContent())
                .eq(!ObjectUtils.isEmpty(hotSearch.getStatus()), HotSearch::getStatus, hotSearch.getStatus())
                .orderByDesc(HotSearch::getSeq, HotSearch::getCreateTime)
        );
    }

    @Override
    public void saveHotSearch(HotSearch hotSearch) {
        hotSearch.setShopId(AuthUtil.getShopId());
        hotSearch.setCreateTime(new Date());
        hotSearchMapper.insert(hotSearch);
    }
}
