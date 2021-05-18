package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmUser;
import lombok.Data;

import java.util.List;

/**
 * @author by cxd
 * @Classname EcmUserVO
 * @Description TODO
 * @Date 2020/8/6 10:50
 */
@Data
public class EcmUserVO extends EcmUser {
    private String oldPassWord;
    private List<EcmUserRolesVO> ecmUserRolesVOS;
    private String roleName;

    //上行流量
    private String upFlow;


    /**
     * 用户下行总流量（单位KB）
     */
    private Integer subTotalFlow;

    /**
     * 用户下行已使用流量（单位KB）
     */
    private Integer subUsedFlow;

    /**
     * 用户下行剩余流量（单位KB）
     */
    private Integer surplusFlow;

}
