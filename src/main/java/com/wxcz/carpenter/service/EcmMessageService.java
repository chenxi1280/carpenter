package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.query.EcmInnerMessageQuery;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;

/**
 * @author by cxd
 * @Classname EcmMessageService
 * @Description TODO
 * @Date 2020/8/19 17:30
 */
public interface EcmMessageService {
    PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmInnerMessageQuery);
}
