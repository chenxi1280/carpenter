package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_artwork_free_ad
 * @author
 */
@Data
public class EcmArtworkFreeAd implements Serializable {
    /**
     * 免广告artwork中间表主键
     */
    private Integer pkEcmArtworkFreeAdId;

    /**
     * 免广告的作品id
     */
    private Integer fkArtworkId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
