package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.constant.StoreConstant;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.IndexImg;
import com.chuanqihou.powershop.mapper.IndexImgMapper;
import com.chuanqihou.powershop.service.IndexImgService;
import org.springframework.util.ObjectUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService{

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    public Page<IndexImg> findIndexImgByPage(Page<IndexImg> page, IndexImg indexImg) {
        return indexImgMapper.selectPage(page, new LambdaQueryWrapper<IndexImg>()
                .eq(!ObjectUtils.isEmpty(indexImg.getStatus()), IndexImg::getStatus, indexImg.getStatus())
                .orderByDesc(IndexImg::getSeq, IndexImg::getCreateTime)
        );
    }

    @Override
    public void saveIndexImg(IndexImg indexImg) {

        indexImg.setImgId(null);
        indexImg.setShopId(AuthUtil.getShopId());
        indexImg.setCreateTime(new Date());

        indexImgMapper.insert(indexImg);
    }

    @Override
    public List<IndexImg> findIndexImgList() {
        return indexImgMapper.selectList(new LambdaQueryWrapper<IndexImg>()
                .eq(IndexImg::getStatus,1)
                .orderByDesc(IndexImg::getSeq)
                .last("limit "+ StoreConstant.INDEX_IMG_NUM)
        );
    }
}
