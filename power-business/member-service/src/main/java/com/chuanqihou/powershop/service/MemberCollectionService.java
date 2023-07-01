package com.chuanqihou.powershop.service;

import com.chuanqihou.powershop.domain.MemberCollection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface MemberCollectionService extends IService<MemberCollection>{


    Long findMemberCollectionNum();

    Boolean isCollection(Long prodId);

    void addOrCancelMemberProCollection(Long prodId);
}
