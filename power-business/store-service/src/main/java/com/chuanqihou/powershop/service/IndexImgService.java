package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.IndexImg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface IndexImgService extends IService<IndexImg>{


    Page<IndexImg> findIndexImgByPage(Page<IndexImg> page, IndexImg indexImg);

    void saveIndexImg(IndexImg indexImg);

    List<IndexImg> findIndexImgList();
}
