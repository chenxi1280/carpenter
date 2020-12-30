package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmUserLightReward;
import com.wxcz.carpenter.pojo.query.EcmLightManagementQuery;
import com.wxcz.carpenter.pojo.query.EcmUserLightQurey;
import com.wxcz.carpenter.pojo.vo.EcmUserLightEventVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightRewardVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightVO;
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

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 :
     *         光 会员 表格 页面
     */
    @RequestMapping("lightVIPPage")
    public String lightVIPPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/light/light-vip-list-page";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 :
     *         光 事件页面
     */
    @RequestMapping("lightEventPage")
    public String lightEventPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/light/light-event-page";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 :  光事件 会员 配置页面
     */
    @RequestMapping("lightRewardPage")
    public String lightRewardPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/light/light-reward-page";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 用户 光数量 查看
     */
    @RequestMapping("userLightPage")
    public String userLightPage() {
        //是否有权限进去
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return "error/error-403";
        }
        return "back/light/user-light-page";
    }

    /**
     * @param: [ecmLightManagementQuery]
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 获取 光会员 list
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("ajaxLightVipList")
    @ResponseBody
    public PageDTO ajaxLightVipList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightVipList(ecmLightManagementQuery);
    }

    /**
     * @param: [ecmLightManagementQuery]
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 获取光事件 list
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("ajaxLightEventList")
    @ResponseBody
    public PageDTO ajaxLightEventList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightEventList(ecmLightManagementQuery);
    }

    /**
     * @param: [ecmLightManagementQuery]
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 获取 光-事件-奖励 list
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("ajaxLightRewardList")
    @ResponseBody
    public PageDTO ajaxLightRewardList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightRewardList(ecmLightManagementQuery);
    }

    /**
     * @param: [ecmUserLightVipVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 新增光 会员
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("addLightVip")
    @ResponseBody
    public ResponseDTO addLightVip(EcmUserLightVipVO ecmUserLightVipVO){
        return ecmLightManagementService.addLightVip(ecmUserLightVipVO);
    }

    /**
     * @param: [ecmUserLightEventVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 :  新增光事件
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("addLightEvent")
    @ResponseBody
    public ResponseDTO addLightEvent(EcmUserLightEventVO ecmUserLightEventVO){
        return ecmLightManagementService.addLightEvent(ecmUserLightEventVO);
    }

    /**
     * @param: [ecmUserLightVipVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 更新光会员
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("updataLightVip")
    @ResponseBody
    public ResponseDTO updataLightVip(EcmUserLightVipVO ecmUserLightVipVO){
        return ecmLightManagementService.updataLightVip(ecmUserLightVipVO);
    }

    /**
     * @param: [ecmUserLightEventVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 :  更新光事件
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("updataLightEvent")
    @ResponseBody
    public ResponseDTO updataLightEvent(EcmUserLightEventVO ecmUserLightEventVO){
        return ecmLightManagementService.updataLightEvent(ecmUserLightEventVO);
    }

    /**
     * @param: [ecmLightManagementQuery]
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 获取光 会员 和 光事件 list
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("ajaxLightVipListAndLightEventList")
    @ResponseBody
    public PageDTO ajaxLightVipListAndLightEventList(EcmLightManagementQuery ecmLightManagementQuery) {
        return ecmLightManagementService.ajaxLightVipListAndLightEventList(ecmLightManagementQuery);
    }

    /**
     * @param: [ecmUserLightRewardVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 新增加 光奖励事件
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("addLightReward")
    @ResponseBody
    public ResponseDTO addLightReward(EcmUserLightRewardVO ecmUserLightRewardVO) {
        return ecmLightManagementService.addLightReward(ecmUserLightRewardVO);
    }

    /**
     * @param: [ecmUserLightRewardVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 更新光 奖励
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("updataLightReward")
    @ResponseBody
    public ResponseDTO updataLightReward(EcmUserLightRewardVO ecmUserLightRewardVO){
        return ecmLightManagementService.updataLightReward(ecmUserLightRewardVO);
    }

    /**
     * @param: [ecmUserLightRewardVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/30
     * 描述 : 删除光奖励
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("delLightReward")
    @ResponseBody
    public ResponseDTO delLightReward(EcmUserLightRewardVO ecmUserLightRewardVO){
        return ecmLightManagementService.delLightReward(ecmUserLightRewardVO);
    }

    @RequestMapping("ajaxUserLightList")
    @ResponseBody
    public PageDTO ajaxUserLightList(EcmUserLightQurey ecmUserLightQurey){
        return ecmLightManagementService.ajaxUserLightList(ecmUserLightQurey);
    }

    @RequestMapping("updateLightVip")
    @ResponseBody
    public ResponseDTO updateLightVip(EcmUserLightVO ecmUserLightVO){
        return ecmLightManagementService.updateLightVip(ecmUserLightVO);
    }
}
