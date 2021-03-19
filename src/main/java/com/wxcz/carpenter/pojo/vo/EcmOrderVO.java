package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmOrder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author by cxd
 * @Classname EcmOrderVO
 * @Description TODO
 * @Date 2021/3/8 13:26
 */
@Data
public class EcmOrderVO extends EcmOrder {


    /**
     * 商品类别名称
     */
    private String categoryName;


    /**
     * 用户名
     */
    private String username;


    /**
     * 商品名称
     */
    private String goodsName;
}
