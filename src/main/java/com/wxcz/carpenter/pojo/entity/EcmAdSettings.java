package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ecm_ad_settings
 * @author
 */
@Data
public class EcmAdSettings implements Serializable {
    /**
     * 广告设置id
     */
    private Integer ecmAdSettingsId;

    /**
     * 广告所在名字
     */
    private String ecmAdName;

    /**
     * 视频数
     */
    private Integer ecmVideoNumber;

    /**
     * 广告
     */
    private Integer ecmAdNumber;

    /**
     * 状态（无用）
     */
    private Integer ecmAdState;

    /**
     * 备注( 未使用）
     */
    private String note;

    private static final long serialVersionUID = 1L;
}
