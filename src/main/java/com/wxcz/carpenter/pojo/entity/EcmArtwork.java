package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_artwork
 * @author
 */
@Data
public class EcmArtwork implements Serializable {
    /**
     * 作品表
     */
    private Integer pkArtworkId;

    /**
     * 作者
     */
    private Integer fkUserid;

    private String artworkName;

    /**
     * 作品描述
     */
    private String artworkDescribe;

    /**
     * 状态，0草稿，1待审核，2审核通过，3，ai审核通过，4，已发布，5被删除
     */
    private Short artworkStatus;

    /**
     * 作品封面存储位置，绝对路径
     */
    private String logoPath;

    /**
     * 图片审核状态
     */
    private Short logoPathStatus;

    private Date lastCreateDate;

    private Date lastModifyDate;

    /**
     * 四字标签
     */
    private String fourLetterTips;

    /**
     * 审核人
     */
    private Integer fkAuditId;


    /**
     * 播放模式
     */
    private Integer playMode;

    /**
     * 是否为多结局
     */
    private Integer isEndings;

    /**
     * 能播放的客户端  null 所有，1 微信，2 抖音
     */
    private Integer playClient;

    private static final long serialVersionUID = 1L;
}
