package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.domain.Member;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/29 13:53
 * @description
 */
@RestController
@RequestMapping("/p/user")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PutMapping("/setUserInfo")
    public Result<Object> editMemberInfo(@RequestBody Member member) {
        memberService.modifyMemberInfo(member);
        return Result.success();
    }

    @PostMapping("/getMemberListByRemoteAndOpenIds")
    public List<Member> getMemberListByRemoteAndOpenIds(@RequestBody List<String> openIds){
        System.out.println("==========="+openIds);
        List<Member> list = memberService.list(new LambdaQueryWrapper<Member>()
                .in(Member::getOpenId, openIds)
        );
        System.out.println("======="+list);
        return list;
    }

    @GetMapping("/getMemberListByRemoteAndOpenIds2")
    public List<Member> getMemberListByRemoteAndOpenIds2(@RequestParam("openIds") List<String> openIds){
        System.out.println("==========="+openIds);
        List<Member> list = memberService.list(new LambdaQueryWrapper<Member>()
                .in(Member::getOpenId, openIds)
        );
        System.out.println("======="+list);
        return list;
    }

}
