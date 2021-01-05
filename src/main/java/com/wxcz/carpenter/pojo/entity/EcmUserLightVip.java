package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ecm_user_light_vip
 * @author
 */
@Data
public class EcmUserLightVip implements Serializable {
    /**
     * 用户光会员id
     */
    private Integer ecmUserLightVipId;

    /**
     * 光会员对应的名字
     */
    private String ecmUserLightVipName;

    /**
     * 会员对应的光上限
     */
    private Integer ecmUserLightUpLimit;

    /**
     * 默认光数量
     */
    private Integer lightDefault;

    private static final long serialVersionUID = 1L;
}
