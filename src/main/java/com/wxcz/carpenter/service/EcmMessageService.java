package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmInnerMessageQuery;
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
}
