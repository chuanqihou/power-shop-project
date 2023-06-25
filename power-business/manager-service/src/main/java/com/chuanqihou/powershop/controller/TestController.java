package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/6/25 15:46
 * @description
 */

@RestController
public class TestController {

    @GetMapping("/get")
    public Result get() {
        return Result.success();
    }

}
