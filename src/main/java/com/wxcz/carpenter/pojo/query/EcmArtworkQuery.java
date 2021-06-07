package com.wxcz.carpenter.pojo.query;

import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmArtworkQuery
 * @Description TODO
 * @Date 2020/8/7 11:14
 */
@Data
public class EcmArtworkQuery extends PageQuery {
    /**
     * 作品表
     */
    private Integer pkArtworkId;

    /**
     * 投诉id
     */
    private Integer reportId;

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
     * 状态，0草稿，1待审核，2已发布审核通过，3审核不通过禁封，5被删除
     */
    private Short artworkStatus;

    /**
     * 四字标签
     */
    private String fourLetterTips;

    /**
     * 能播放的客户端  null 所有，1 微信，2 抖音 ,3 H5
     */
    private Integer playClient;

    /**
     * 版本名称
     */
    private String versionId;


    /**
     * 1 免广告播放作品 2 白名单作品
     */
    private Integer playType;

}
