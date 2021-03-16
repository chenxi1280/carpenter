package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_vip_authority
 * @author
 */
@Data
public class EcmVipAuthority implements Serializable {
    private Integer pkAuthorityId;

    /**
     * 普通会员,超级会员
     */
    private String vipAuthorityDescribe;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
