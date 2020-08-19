package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmInnerMessageDao;
import com.wxcz.carpenter.dao.EcmTemplateDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.entity.EcmTemplate;
import com.wxcz.carpenter.pojo.query.EcmInnerMessageQuery;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;
import com.wxcz.carpenter.service.EcmMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by cxd
 * @Classname EcmMessageServiceImpl
 * @Description TODO
 * @Date 2020/8/19 17:30
 */
@Service
public class EcmMessageServiceImpl implements EcmMessageService {
    @Resource
    EcmInnerMessageDao ecmInnerMessageDao;

    @Resource
    EcmTemplateDao ecmTemplateDao;

    @Override
    public PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmTemplateQuery) {
//        List<EcmInnerMessageVO> ecmInnerMessageVOS = ecmInnerMessageDao.ajaxMsgTemplateList(ecmInnerMessageQuery);
        List<EcmTemplateVo> ecmTemplateVos = ecmTemplateDao.ajaxMsgTemplateList(ecmTemplateQuery);
        Integer count = ecmTemplateDao.ajaxMsgTemplateCount(ecmTemplateQuery);

        return PageDTO.setPageData(count,ecmTemplateVos);
    }
}
