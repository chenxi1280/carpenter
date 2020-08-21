package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;

/**
 * @author by cxd
 * @Classname EcmMessageService
 * @Description TODO
 * @Date 2020/8/19 17:30
 */
public interface EcmMessageService {

    PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmInnerMessageQuery);

    ResponseDTO updataMsgTemplate(EcmTemplateVo ecmTemplateVo);

    ResponseDTO delMsgTemplate(EcmTemplateQuery ecmTemplateQuery);

    ResponseDTO addMsgTemplate(EcmTemplateVo ecmTemplateVo);


    ResponseDTO addMsgAll(Integer pkTemplateId);

    ResponseDTO addMsgPart(EcmTemplateVo ecmTemplateVo);


    //    作品审核站内信
    Integer insertSystemMsg(EcmArtwork ecmArtwork, String template);
    // 作品违规站内信
    Integer insertViolationMsg(EcmReportHistroy ecmReportHistroy, String template);
}
