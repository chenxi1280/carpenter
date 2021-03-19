package com.wxcz.carpenter.pojo.query;

import lombok.Data;

import java.util.Date;

/**
 * @author by SJ
 * @Classname EcmVipAuthorityQuery
 * @Description TODO
 * @Date 2021/3/16 18:24
 */
@Data
public class EcmVipAuthorityQuery extends PageQuery {
    private Integer pkAuthorityId;

    /**
     * 普通会员,超级会员
     */
    private String vipAuthorityDescribe;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
