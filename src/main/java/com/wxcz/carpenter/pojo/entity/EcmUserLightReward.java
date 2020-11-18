package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;

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

    private static final long serialVersionUID = 1L;
}
