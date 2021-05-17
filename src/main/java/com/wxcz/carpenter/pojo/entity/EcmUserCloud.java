package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_user_cloud
 * @author
 */
@Data
public class EcmUserCloud implements Serializable {
    /**
     * 用户私有云管理表
     */
    private Integer pkUserCloudId;

    /**
     * RequestId请求id
     */
    private String requestId;

    /**
     * SubAppId子应用id
     */
    private String subAppId;

    /**
     * 用户id
     */
    private Integer fkUserId;

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
