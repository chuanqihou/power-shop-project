package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.ProdComm;
import com.chuanqihou.powershop.mapper.ProdCommMapper;
import com.chuanqihou.powershop.service.ProdCommService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService{

    @Autowired
    private ProdCommMapper prodCommMapper;

    @Override
    public Page<ProdComm> findProCommPage(Page<ProdComm> page, ProdComm prodComm) {
        return prodCommMapper.selectPage(page, new LambdaQueryWrapper<ProdComm>()
                //.eq(!AuthUtil.getShopId().equals(prodComm.getShopId()), ProdComm::getShopId, prodComm.getShopId())
                .like(StringUtils.hasText(prodComm.getProdName()), ProdComm::getProdName, prodComm.getProdName())
                .eq(!ObjectUtils.isEmpty(prodComm.getStatus()), ProdComm::getStatus, prodComm.getStatus())
        );
    }

    @Override
    public void modifyProdComm(ProdComm prodComm) {
        ProdComm newProdComm = prodCommMapper.selectById(prodComm.getProdCommId());
        newProdComm.setReplyContent(prodComm.getReplyContent());
        newProdComm.setStatus(prodComm.getStatus());
        prodCommMapper.updateById(prodComm);
    }
}
