package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.HotSearch;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.HotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/28 9:20
 * @description
 */
@RestController
@RequestMapping("/admin/hotSearch")
public class HotSearchController {

    @Autowired
    private HotSearchService hotSearchService;

    @GetMapping("/page")
    public Result<Page<HotSearch>> getHotSearchByPage(Page<HotSearch> page, HotSearch hotSearch) {
        Page<HotSearch> hotSearchPage = hotSearchService.findHotSearchByPage(page, hotSearch);
        return Result.success(hotSearchPage);
    }

    @PostMapping
    public Result<Object> addHotSearch(@RequestBody HotSearch hotSearch) {
        hotSearchService.saveHotSearch(hotSearch);
        return Result.success();
    }

    @GetMapping("/hotSearchByShopId")
    public Result<List<HotSearch>> hotSearchByShopId(Long number, Long shopId, Long sort) {

        List<HotSearch> hotSearchList = hotSearchService.findHotSearchKey(number, shopId, sort);

        return Result.success(hotSearchList);
    }

}
