package com.wxcz.carpenter.pojo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
     * 状态（0 未使用）(1 使用)
     */
    private Integer ecmBannerState;

    /**
     * banner地址
     */
    private String ecmBannerAddress;

    /**
     * 鏈接地址
     */
    private String ecmBannerConnectionUrl;

    /**
     * 连接状态
     */
    private Integer ecmBannerConnectionState;

    /**
     * 创建日期
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;
}
