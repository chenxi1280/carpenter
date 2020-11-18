package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmUserLightReward;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmUserLightRewardVO
 * @Description TODO
 * @Date 2020/11/16 18:05
 */
@Data
public class EcmUserLightRewardVO  extends EcmUserLightReward {
    /**
     * 有关光的事件name
     */
    private String ecmUserLightEventName;

    /**
     * 光会员对应的名字
     */
    private String ecmUserLightVipName;
}
