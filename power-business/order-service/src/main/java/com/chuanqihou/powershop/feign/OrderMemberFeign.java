package com.chuanqihou.powershop.feign;

import com.chuanqihou.powershop.domain.MemberAddr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 传奇后
 * @date 2023/7/4 11:40
 * @description
 */
@FeignClient("member-service")
public interface OrderMemberFeign {

    @GetMapping("/p/address/getMemberAddrByOpenId")
    MemberAddr getMemberAddrRemoteByOpenId(@RequestParam("loginMemberOpenId") String loginMemberOpenId,@RequestParam("addrId") Long addrId);

}
