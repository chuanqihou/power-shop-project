package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanqihou.powershop.domain.HotSearch;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface HotSearchService extends IService<HotSearch>{


    Page<HotSearch> findHotSearchByPage(Page<HotSearch> page, HotSearch hotSearch);

    void saveHotSearch(HotSearch hotSearch);

    List<HotSearch> findHotSearchKey(Long number, Long shopId, Long sort);
}
