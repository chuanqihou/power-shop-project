package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.MemberCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/isCollection")
    public Result<Object> isCollection(Long prodId) {
        Boolean result = memberCollectionService.isCollection(prodId);
        return Result.success(result);
    }

    @PostMapping("/addOrCancel")
    public Result<Object> addOrCancelMemberProCollection(@RequestBody Long prodId) {
        memberCollectionService.addOrCancelMemberProCollection(prodId);
        return Result.success();
    }

}
