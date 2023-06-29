package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.MemberCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/6/29 19:29
 * @description
 */
@RestController
@RequestMapping("/p/collection")
public class MemberCollectionController {

    @Autowired
    private MemberCollectionService memberCollectionService;

    @GetMapping("/count")
    public Result<Long> getMemberCollectionNum() {
        Long num = memberCollectionService.findMemberCollectionNum();
        return Result.success(num);
    }

}
