package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmLightManagementQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserLightEventVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightRewardVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightVipVO;
import com.wxcz.carpenter.service.EcmLightManagementService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname EcmLightManagementController
 * @Description TODO
 * @Date 2020/11/16 17:11
 */
@Controller
@RequestMapping("back/lightManagement")
public class EcmLightManagementController {

    @Resource
    EcmLightManagementService ecmLightManagementService;

    @RequestMapping("lightVIPPage")
    public String lightVIPPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/light/light-vip-list-page";
    }

    @RequestMapping("lightEventPage")
    public String lightEventPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/light/light-event-page";
    }

    @RequestMapping("lightRewardPage")
    public String lightRewardPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/light/light-reward-page";
    }

    @RequestMapping("ajaxLightVipList")
    @ResponseBody
    public PageDTO ajaxLightVipList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightVipList(ecmLightManagementQuery);
    }

    @RequestMapping("ajaxLightEventList")
    @ResponseBody
    public PageDTO ajaxLightEventList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightEventList(ecmLightManagementQuery);
    }
    @RequestMapping("ajaxLightRewardList")
    @ResponseBody
    public PageDTO ajaxLightRewardList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightRewardList(ecmLightManagementQuery);
    }

    @RequestMapping("addLightVip")
    @ResponseBody
    public ResponseDTO addLightVip(EcmUserLightVipVO ecmUserLightVipVO){
        return ecmLightManagementService.addLightVip(ecmUserLightVipVO);
    }

    @RequestMapping("addLightEvent")
    @ResponseBody
    public ResponseDTO addLightEvent(EcmUserLightEventVO ecmUserLightEventVO){
        return ecmLightManagementService.addLightEvent(ecmUserLightEventVO);
    }

    @RequestMapping("updataLightVip")
    @ResponseBody
    public ResponseDTO updataLightVip(EcmUserLightVipVO ecmUserLightVipVO){
        return ecmLightManagementService.updataLightVip(ecmUserLightVipVO);
    }

    @RequestMapping("updataLightEvent")
    @ResponseBody
    public ResponseDTO updataLightEvent(EcmUserLightEventVO ecmUserLightEventVO){
        return ecmLightManagementService.updataLightEvent(ecmUserLightEventVO);
    }

    @RequestMapping("ajaxLightVipListAndLightEventList")
    @ResponseBody
    public PageDTO ajaxLightVipListAndLightEventList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightVipListAndLightEventList(ecmLightManagementQuery);
    }

    @RequestMapping("addLightReward")
    @ResponseBody
    public ResponseDTO addLightReward(EcmUserLightRewardVO ecmUserLightRewardVO) {
        return ecmLightManagementService.addLightReward(ecmUserLightRewardVO);
    }



    @RequestMapping("updataLightReward")
    @ResponseBody
    public ResponseDTO updataLightReward(EcmUserLightRewardVO ecmUserLightRewardVO){
        return ecmLightManagementService.updataLightReward(ecmUserLightRewardVO);
    }
    @RequestMapping("delLightReward")
    @ResponseBody
    public ResponseDTO delLightReward(EcmUserLightRewardVO ecmUserLightRewardVO){
        return ecmLightManagementService.delLightReward(ecmUserLightRewardVO);
    }
}
