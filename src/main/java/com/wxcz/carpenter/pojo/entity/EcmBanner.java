package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_banner
 * @author
 */
@Data
public class EcmBanner implements Serializable {
    /**
     * bannerId
     */
    private Integer ecmBannerId;

    /**
     * banner名称
     */
    private String ecmBannerName;

    /**
     * 状态（未使用）
     */
    private Integer ecmBannerState;

    /**
     * baner地址
     */
    private String ecmBannerAddress;

    /**
     * 创建日期
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;
}
