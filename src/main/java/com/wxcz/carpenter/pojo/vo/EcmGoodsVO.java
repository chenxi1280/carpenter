package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmGoods;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmGoodsVO
 * @Description TODO
 * @Date 2021/3/8 11:21
 */
@Data
public class EcmGoodsVO extends EcmGoods {

    /**
     * 类目名称
     */
    private String categoryName;
}
