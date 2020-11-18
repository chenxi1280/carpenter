package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ecm_user_light_event
 * @author
 */
@Data
public class EcmUserLightEvent implements Serializable {
    /**
     * 用户有关光的事件id
     */
    private Integer ecmUserLightEventId;

    /**
     * 有关光的事件name
     */
    private String ecmUserLightEventName;

    private static final long serialVersionUID = 1L;
}
