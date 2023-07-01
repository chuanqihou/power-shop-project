package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanqihou.powershop.ex.MemberServiceException;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.MemberCollection;
import com.chuanqihou.powershop.mapper.MemberCollectionMapper;
import com.chuanqihou.powershop.service.MemberCollectionService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class MemberCollectionServiceImpl extends ServiceImpl<MemberCollectionMapper, MemberCollection> implements MemberCollectionService{

    @Autowired
    private MemberCollectionMapper memberCollectionMapper;

    @Override
    public Long findMemberCollectionNum() {
        return memberCollectionMapper.selectCount(new LambdaQueryWrapper<MemberCollection>()
                .eq(MemberCollection::getOpenId, AuthUtil.getLoginMemberOpenId())
        );
    }

    @Override
    public Boolean isCollection(Long prodId) {
        Long count = memberCollectionMapper.selectCount(new LambdaQueryWrapper<MemberCollection>()
                .eq(MemberCollection::getProdId, prodId)
                .eq(MemberCollection::getOpenId,AuthUtil.getLoginMemberOpenId())
        );
        if (count == 0) {
            //throw new MemberServiceException("该商品被未收藏");
            return null;
        }
        return true;
    }

    @Override
    @Transactional
    public void addOrCancelMemberProCollection(Long prodId) {
        Long count = memberCollectionMapper.selectCount(new LambdaQueryWrapper<MemberCollection>()
                .eq(MemberCollection::getProdId, prodId)
                .eq(MemberCollection::getOpenId,AuthUtil.getLoginMemberOpenId())
        );
        if (count == 0) {
            // 新增收藏（添加）
            MemberCollection memberCollection = new MemberCollection();
            memberCollection.setProdId(prodId);
            memberCollection.setOpenId(AuthUtil.getLoginMemberOpenId());
            memberCollection.setCreateTime(new Date());
            memberCollectionMapper.insert(memberCollection);
        } else {
            // 取消收藏（删除）
            memberCollectionMapper.delete(new LambdaQueryWrapper<MemberCollection>()
                    .eq(MemberCollection::getProdId, prodId)
                    .eq(MemberCollection::getOpenId,AuthUtil.getLoginMemberOpenId())
            );
        }
    }

}
