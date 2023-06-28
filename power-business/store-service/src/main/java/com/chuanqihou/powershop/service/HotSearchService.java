package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.HotSearch;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface HotSearchService extends IService<HotSearch>{


    Page<HotSearch> findHotSearchByPage(Page<HotSearch> page, HotSearch hotSearch);

    void saveHotSearch(HotSearch hotSearch);
}
