package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author by cxd
 * @Classname EcmArtworkVO
 * @Description TODO
 * @Date 2020/8/7 11:17
 */
@Data
public class EcmArtworkVO extends EcmArtwork {

    private String username;

    private String fkAuditName;

    /**
     * 后台 版本管理 是否选中
     */
    private Boolean checked;

    private String versionId;

    private Boolean isChecked;


    /**
     * 用户下行总流量（单位KB）
     */
    private Integer subTotalFlow;

    /**
     * 用户下行已使用流量（单位KB）
     */
    private Integer subUsedFlow;

    /**
     * 用户下行剩余流量（单位KB）
     */
    private Integer surplusFlow;

    /**
     * 播放次数
     */
    private Integer playCount;

    /**
     * 是否 有存储桶用户
     */
    private Boolean isDownLinkUser;

    /**
     * 是否 有存储桶用户
     */
    private Integer pkDownFowNumber;


    private List<Integer> fkArtworkIdList;
    private List<Integer> unFkArtworkIdList;

}
