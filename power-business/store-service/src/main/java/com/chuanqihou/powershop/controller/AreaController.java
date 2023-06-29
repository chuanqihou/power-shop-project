package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.domain.Area;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author 传奇后
 * @date 2023/6/28 8:54
 * @description
 */
@RestController
@RequestMapping("/admin/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/list")
    public Result<List<Area>> getAllArea() {
        List<Area> areaList = areaService.findAllAreaList();
        return Result.success(areaList);
    }

    @GetMapping("/listByPid")
    public Result<List<Area>> findAreaListByPid(Long pid) {
        List<Area> areaList = areaService.findAreaListByPid(pid);
        return Result.success(areaList);
    }
}
