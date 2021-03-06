package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import lombok.Data;

import java.io.Serializable;

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
}
