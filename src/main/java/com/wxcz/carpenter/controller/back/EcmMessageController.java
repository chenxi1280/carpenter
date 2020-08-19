package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.entity.EcmTemplate;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.EcmInnerMessageQuery;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.service.EcmMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname EcmMessageController
 * @Description TODO
 * @Date 2020/8/19 17:30
 */
@Controller
@RequestMapping("back/message")
public class EcmMessageController {
    @Resource
    EcmMessageService ecmMessageService;

    @RequestMapping("msgTemplatePage")
    public String artWorkPage() {
        return "back/message/msg-template-list";
    }

    @RequestMapping("ajaxMsgTemplateList")
    @ResponseBody
    public PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmTemplateQuery) {
        return ecmMessageService.ajaxMsgTemplateList(ecmTemplateQuery);
    }
}
