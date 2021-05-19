package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_downlink_flow_update_history
 * @author
 */
@Data
public class EcmDownlinkFlowUpdateHistory implements Serializable {
    /**
     * 主键
     */
    private Integer pkId;

    /**
     * 用户id
     */
    private Integer fkUserId;

    /**
     * 点播子应用id
     */
    private Integer subAppId;

    /**
     * 用户充值的下行流量（单位KB）
     */
    private Integer subFlow;

    /**
     * 记录创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
