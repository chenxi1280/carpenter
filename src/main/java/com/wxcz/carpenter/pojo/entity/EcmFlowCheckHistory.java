package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_flow_check_history
 * @author 
 */
@Data
public class EcmFlowCheckHistory implements Serializable {
    private Integer flowCheckId;

    /**
     * 用户id
     */
    private Integer fkUserId;

    /**
     * 旧的流量(修改前的)
     */
    private Integer oldFlow;

    /**
     * 新的流量(修改后的流量)
     */
    private Integer newFlow;

    /**
     * 修改时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}