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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
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

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 跳转到编辑msg页面方法
     */
    @RequestMapping("msgTemplatePage")
    public String artWorkPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/message/msg-template-list";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("msgUserPage")
    public String msgUserPage() {
        return "back/message/user-msg-list";
    }

    /**
     * @param: [ecmTemplateQuery] 查询 msg模板方法 自带分页 0，10
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : layui 查询 msg模板 方法
     */
    @RequestMapping("ajaxMsgTemplateList")
    @ResponseBody
    public PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmTemplateQuery) {
        return ecmMessageService.ajaxMsgTemplateList(ecmTemplateQuery);
    }

    /**
     * @param: [ecmTemplateVo] 需要更新的 ecmTemplate id 和 更新值
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("updataMsgTemplate")
    @ResponseBody
    public ResponseDTO updataMsgTemplate(EcmTemplateVo ecmTemplateVo){
        return ecmMessageService.updataMsgTemplate(ecmTemplateVo);
    }

    /**
     * @param: [ecmTemplateQuery] 需要带需要删除的 msg 模板 id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 删除msg模板接口
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("delMsgTemplate")
    @ResponseBody
    public ResponseDTO delMsgTemplate(EcmTemplateQuery ecmTemplateQuery){
        return ecmMessageService.delMsgTemplate(ecmTemplateQuery);
    }

    /**
     * @param: [ecmTemplateVo] 新建的msg 模板 数据
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 新建msg 模板 接口
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("addMsgTemplate")
    @ResponseBody
    public ResponseDTO addMsgTemplate(EcmTemplateVo ecmTemplateVo){
        return ecmMessageService.addMsgTemplate(ecmTemplateVo);
    }

    /**
     * @param: [ecmTemplateVo]  msg 模板 id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  给 所有用户发送站内信接口
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("addMsgAll")
    @ResponseBody
    public ResponseDTO addMsgAll(EcmTemplateVo ecmTemplateVo){
        return ecmMessageService.addMsgAll(ecmTemplateVo.getPkTemplateId());
    }

    /**
     * @param: [EcmTemplateVO] 发送 用户的 ids ， 发送的 msg模板 id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 发送 选中用户 站内信接口
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("addMsgPart")
    @ResponseBody
    public ResponseDTO addMsgPart( @RequestBody  EcmTemplateVo EcmTemplateVO){
        return ecmMessageService.addMsgPart(EcmTemplateVO);

    }
}
