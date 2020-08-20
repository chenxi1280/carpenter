package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.controller.BaseController;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.entity.EcmTemplate;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.EcmInnerMessageQuery;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;
import com.wxcz.carpenter.service.EcmMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
public class EcmMessageController extends BaseController {
    @Resource
    EcmMessageService ecmMessageService;

    @RequestMapping("msgTemplatePage")
    public String artWorkPage() {
        return "back/message/msg-template-list";
    }


    @RequestMapping("msgUserPage")
    public String msgUserPage() {
        return "back/message/user-msg-list";
    }

    @RequestMapping("ajaxMsgTemplateList")
    @ResponseBody
    public PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmTemplateQuery) {
        return ecmMessageService.ajaxMsgTemplateList(ecmTemplateQuery);
    }

    @RequestMapping("updataMsgTemplate")
    @ResponseBody
    public ResponseDTO updataMsgTemplate(EcmTemplateVo ecmTemplateVo){
        return ecmMessageService.updataMsgTemplate(ecmTemplateVo);
    }

    @RequestMapping("delMsgTemplate")
    @ResponseBody
    public ResponseDTO delMsgTemplate(EcmTemplateQuery ecmTemplateQuery){
        return ecmMessageService.delMsgTemplate(ecmTemplateQuery);
    }

    @RequestMapping("addMsgTemplate")
    @ResponseBody
    public ResponseDTO addMsgTemplate(EcmTemplateVo ecmTemplateVo){
        return ecmMessageService.addMsgTemplate(ecmTemplateVo);
    }

    @RequestMapping("addMsgAll")
    @ResponseBody
    public ResponseDTO addMsgAll(EcmTemplateVo ecmTemplateVo){
        return ecmMessageService.addMsgAll(ecmTemplateVo.getPkTemplateId());
    }

    @RequestMapping("addMsgPart")
    @ResponseBody
    public ResponseDTO addMsgPart( @RequestBody  EcmTemplateVo EcmTemplateVo){
        return ecmMessageService.addMsgPart(EcmTemplateVo);

    }
}
