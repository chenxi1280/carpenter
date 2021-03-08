package com.wxcz.carpenter.pojo.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author by cxd
 * @Classname EcmOrderQuery
 * @Description TODO
 * @Date 2021/3/8 13:24
 */
@Data
public class EcmOrderQuery extends PageQuery{

    /**
     * 订单主键
     */
    private Integer pkOrderId;

    /**
     * 订单code
     */
    private String orderCode;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 订单金额
     */
    private BigDecimal orderPrice;

    /**
     * 订单状态
     */
    private Integer orderState;

    /**
     * 商品数量
     */
    private Integer orderGoodsNumber;

    /**
     * 商品id
     */
    private Integer fkGoodsId;

    /**
     * 用户id
     */
    private Integer fkUserId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 商品类别名称
     */
    private Integer categoryName;
}
