package com.wxcz.carpenter.pojo.query;

import com.wxcz.carpenter.pojo.query.PageQuery;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmArtworkVersionInfoQuery
 * @Description TODO
 * @Date 2021/4/6 11:23
 */
@Data
public class EcmArtworkVersionInfoQuery extends PageQuery {
    /**
     * 版本名称
     */
    private String versionId;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 能播放的客户端  null 所有，1 微信，2 抖音 ，3 H5
     */
    private Integer playClient;

    /**
     * 状态，0草稿，1待审核，2已发布审核通过，3审核不通过禁封，5被删除
     */
    private Short artworkStatus;

}
