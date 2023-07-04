package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.domain.MemberAddr;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.MemberAddrService;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/29 18:34
 * @description
 */
@RestController
@RequestMapping("/p/address")
public class MemberAddrController {

    @Autowired
    private MemberAddrService memberAddrService;

    @GetMapping("/list")
    public Result<List<MemberAddr>> getMemberAddrList() {
        String loginMemberOpenId = AuthUtil.getLoginMemberOpenId();
        List<MemberAddr> memberAddrList = memberAddrService.findMemberAddrList(loginMemberOpenId);
        return Result.success(memberAddrList);
    }

    @PutMapping("/defaultAddr/{addrId}")
    public Result<Object> editDefaultAddr(@PathVariable("addrId") Long addrId) {
        memberAddrService.modifyDefaultAddr(addrId,AuthUtil.getLoginMemberOpenId());
        return Result.success();
    }

    @PostMapping
    public Result<Object> addMemberAddr(@RequestBody MemberAddr memberAddr) {
        memberAddrService.saveMemberAddr(memberAddr,AuthUtil.getLoginMemberOpenId());
        return Result.success();
    }

    @GetMapping("/addrInfo/{addrId}")
    public Result<MemberAddr> getMemberAddrInfoById(@PathVariable("addrId") Long addrId) {
        MemberAddr memberAddr = memberAddrService.getById(addrId);
        return Result.success(memberAddr);
    }

    @PutMapping("/updateAddr")
    public Result<Object> editMemberAddr(@RequestBody MemberAddr memberAddr) {
        memberAddrService.modifyMemberAddr(memberAddr,AuthUtil.getLoginMemberOpenId());
        return Result.success();
    }

    @DeleteMapping("/deleteAddr/{addrId}")
    public Result<Object> cutMemberAddrById(@PathVariable("addrId") Long addrId) {
        memberAddrService.removeSoftMemberAddrById(addrId,AuthUtil.getLoginMemberOpenId());
        return Result.success();
    }

    @GetMapping("/getMemberAddrByOpenId")
    MemberAddr getMemberAddrRemoteByOpenId(@RequestParam("loginMemberOpenId") String loginMemberOpenId,@RequestParam("addrId") Long addrId){
        return memberAddrService.list(new LambdaQueryWrapper<MemberAddr>()
                .eq(MemberAddr::getOpenId, loginMemberOpenId)
                .eq(addrId.equals(0L),MemberAddr::getCommonAddr, 1)
                .eq(!addrId.equals(0L),MemberAddr::getAddrId, addrId)
                .eq(MemberAddr::getStatus, 1)
        ).get(0);
    }

}
