package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.IndexImg;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.IndexImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 传奇后
 * @date 2023/6/28 9:05
 * @description
 */
@RestController
@RequestMapping("/admin/indexImg")
public class IndexImgController {

    @Autowired
    private IndexImgService indexImgService;

    @GetMapping("/page")
    public Result<Page<IndexImg>> getIndexImgByPage(Page<IndexImg> page, IndexImg indexImg) {
        Page<IndexImg> indexImgPage = indexImgService.findIndexImgByPage(page, indexImg);
        return Result.success(indexImgPage);
    }

    @PostMapping
    public Result<Object> addIndexImg(@RequestBody IndexImg indexImg) {
        indexImgService.saveIndexImg(indexImg);
        return Result.success();
    }

}
