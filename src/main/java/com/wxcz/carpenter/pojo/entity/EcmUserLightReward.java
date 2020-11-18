package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_user_light_reward
 * @author
 */
@Data
public class EcmUserLightReward implements Serializable {
    /**
     * 会员奖励表
     */
    private Integer ecmUserLightRewardId;

    /**
     * 用户会员光id
     */
    private Integer fkEcmUserLightVipId;

    /**
     * 用户光事件id
     */
    private Integer fkEcmUserLightEventId;

    /**
     * 奖励值计算式
     */
    private String rewardLight;

    /**
     * 状态
     */
    private Integer rewardState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updataTime;

    private static final long serialVersionUID = 1L;
}
