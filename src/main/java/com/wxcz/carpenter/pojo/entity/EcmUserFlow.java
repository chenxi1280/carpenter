package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_user_flow
 * @author 
 */
@Data
public class EcmUserFlow implements Serializable {
    /**
     * 主键
     */
    private Integer userFlowId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 总流量（单位KB）
     */
    private Integer totalFlow;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 上一次的审核流量（单位KB）
     */
    private Integer checkFlow;

    /**
     * 用户已使用的普通流量（单位KB）
     */
    private Integer usedFlow;

    /**
     * 用户的永久使用流量（单位KB）
     */
    private Integer permanentFlow;

    private static final long serialVersionUID = 1L;
}