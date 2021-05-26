package com.wxcz.carpenter.service;

public interface EcmDownLinkFlowService {


    long describeCDNStatDetails(String sTime,String eTime,long subAppId);

    Long createSubAppId(String name);

    /**
     * @author SJ
     *  修改子应用状态
     * @param status On：启用。 Off：停用。Destroyed：销毁。
     * @param subAppId
     * @return
     */
    boolean modifySubAppStatus(String status, Long subAppId);
}
