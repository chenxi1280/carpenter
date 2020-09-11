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
     * 剩余流量
     */
    private Integer surplusFlow;

    /**
     * 总流量
     */
    private Integer totalFlow;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}