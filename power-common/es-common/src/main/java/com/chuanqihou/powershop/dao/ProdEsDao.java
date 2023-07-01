package com.chuanqihou.powershop.dao;

import com.chuanqihou.powershop.model.ProdEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 传奇后
 * @date 2023/6/30 14:47
 * @description
 */
@Repository
public interface ProdEsDao extends ElasticsearchRepository<ProdEs,Long> {

}
