package com.wxcz.carpenter.pojo.query;

import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmMerchantQuery
 * @Description TODO
 * @Date 2021/2/22 10:58
 */
@Data
public class EcmMerchantQuery extends PageQuery{
    /**
     * 商户id
     */
    private Integer pkMerchantId;

    /**
     * 对应的用户id
     */
    private Integer fkUserId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商家联系电话
     */
    private String mobilePhone;

    /**
     * 商家简介
     */
    private String merchantDetail;

    /**
     * 邮箱
     */
    private String email;

}
