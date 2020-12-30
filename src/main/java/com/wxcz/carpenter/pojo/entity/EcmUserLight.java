package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_user_light
 * @author
 */
@Data
public class EcmUserLight implements Serializable {
    private Integer pkId;

    /**
     * userId
     */
    private Integer fkUserId;

    /**
     * 用户光数量
     */
    private Integer lightNumber;

    /**
     * 光会员id
     */
    private Integer fkUserLightVipId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updataTime;

    private static final long serialVersionUID = 1L;
}
