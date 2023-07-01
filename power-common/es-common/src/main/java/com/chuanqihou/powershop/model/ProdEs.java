package com.chuanqihou.powershop.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/30 14:00
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setting
@Document(indexName = "product_es_index_cxs",shards = 2, replicas = 1, refreshInterval = "1s")
@ApiModel("es商品对象")
public class ProdEs {

    @Id
    @Field(type = FieldType.Keyword)
    private Long prodId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String prodName;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Field(type = FieldType.Long)
    private Integer soldNum;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String brief;

    @Field(type = FieldType.Text)
    private String pic;

    @Field(type = FieldType.Integer)
    private Integer status;

    @Field(type = FieldType.Integer)
    private Integer totalStocks;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Text)
    private List<Long> tagList;

    @Field(type = FieldType.Long)
    private Long praiseNumber = 0L;

    @Field(type = FieldType.Double)
    private BigDecimal positiveRating = BigDecimal.ZERO;
}
