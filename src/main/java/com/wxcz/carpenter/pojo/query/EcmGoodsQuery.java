package com.wxcz.carpenter.pojo.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author by cxd
 * @Classname EcmGoodsQuery
 * @Description TODO
 * @Date 2021/3/8 11:04
 */
@Data
public class EcmGoodsQuery extends PageQuery{
    /**
     * 商品id
     */
    private Integer pkGoodsId;
    /**
     * 商品类目表
     */
    private Integer fkGoodsCategoryId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品状态
     */
    private Integer goodsState;


    /**
     * 商品类别名称
     */
    private String categoryName;
}
