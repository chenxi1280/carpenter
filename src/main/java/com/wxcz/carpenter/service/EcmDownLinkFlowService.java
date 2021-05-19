package com.wxcz.carpenter.service;

public interface EcmDownLinkFlowService {


    long describeCDNStatDetails(String sTime,String eTime,long subAppId);

    Long createSubAppId(String name);
}
