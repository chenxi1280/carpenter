package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ecm_goods
 * @author
 */
@Data
public class EcmGoods implements Serializable {
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
     * 说明
     */
    private String goodContext;

    /**
     * 流量包
     */
    private Integer goodFlow;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
