package com.chuanqihou.powershop.service;

import com.chuanqihou.powershop.domain.Area;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface AreaService extends IService<Area>{


    List<Area> findAllAreaList();

    List<Area> findAreaListByPid(Long pid);
}
