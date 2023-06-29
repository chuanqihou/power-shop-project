package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.domain.Member;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 传奇后
 * @date 2023/6/29 13:53
 * @description
 */
@RestController
@RequestMapping("/p/user/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PutMapping("/setUserInfo")
    public Result<Object> editMemberInfo(@RequestBody Member member) {
        memberService.modifyMemberInfo(member);
        return Result.success();
    }

}
