package com.chuanqihou.powershop.listener;

import com.chuanqihou.powershop.dao.ProdEsDao;
import com.chuanqihou.powershop.model.ProdChange;
import com.chuanqihou.powershop.model.ProdEs;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/7/5 8:51
 * @description
 */
@Component
public class ProdEsMsgListener implements RocketMQListener<List<ProdChange>> {

    @Autowired
    private ProdEsDao prodEsDao;

    @Override
    public void onMessage(List<ProdChange> prodChanges) {
        // 消息队列 消费端代码
        List<ProdEs> prodEsList = new ArrayList<>();
        prodChanges.forEach(prodChange -> {
            ProdEs prodEs = prodEsDao.findById(prodChange.getProdId()).get();
            if (!ObjectUtils.isEmpty(prodEs)) {
                prodEs.setTotalStocks(prodEs.getTotalStocks() + prodChange.getProdCount());
                prodEs.setSoldNum(prodEs.getSoldNum() - prodChange.getProdCount());
            }
            Long prodId = prodChange.getProdId();
        });

    }
}
