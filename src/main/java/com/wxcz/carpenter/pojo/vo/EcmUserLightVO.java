package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmUserLight;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmUserLightVO
 * @Description TODO
 * @Date 2020/12/30 11:23
 */
@Data
public class EcmUserLightVO extends EcmUserLight {


    /**
     * 用户名
     */
    private String username;


    /**
     * 光会员对应的名字
     */
    private String ecmUserLightVipName;

}
