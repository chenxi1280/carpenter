package com.wxcz.carpenter.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by cxd
 * @Classname EcmArtworkVersionInfoDTO
 * @Description TODO
 * @Date 2021/4/6 16:37
 */
@Data
public class EcmArtworkVersionInfoDTO implements Serializable {
    /**
     * 主键id
     */
    private Integer pkId;

    /**
     * 作品id
     */
    private Integer fkArtworkId;

    /**
     * 版本号
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    private static final long serialVersionUID = 1L;
}
