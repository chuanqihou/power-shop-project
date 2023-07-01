package com.chuanqihou.powershop.feign;

import com.chuanqihou.powershop.domain.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/1 19:23
 * @description
 */
@FeignClient("member-service")
public interface ProductMemberFeign {

    @PostMapping("/p/user/getMemberListByRemoteAndOpenIds")
    List<Member> getMemberListByRemoteAndOpenIds(List<String> openIds);

}
