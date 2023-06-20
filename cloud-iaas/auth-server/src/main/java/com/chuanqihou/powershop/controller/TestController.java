package com.chuanqihou.powershop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/6/20 12:03
 * @description
 */
@RestController
public class TestController {

    @GetMapping("/getInfo")
    public String infor() {
        return "success";
    }

}
