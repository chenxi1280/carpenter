package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.service.VipAuthorityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by SJ
 * @Classname VipAuthorityController
 * @Description TODO
 * @Date 2021/3/16 17:10
 */
@Controller
@RequestMapping("back/vip")
public class VipAuthorityController {

    @Resource
    VipAuthorityService vipAuthorityService;

    /**
     * @param: []
     * @return: java.lang.String
     * @author: SJ
     * @Date: 2021/3/16
     * 描述 : 会员管理配置页面跳转
     */
    @RequestMapping("adSettingsPage")
    public String adSettingsPage() {
        return "back/vip/vipAuthority";
    }

    @RequestMapping("ajaxVipAuthorityList")
    @ResponseBody
    public PageDTO ajaxVipAuthorityList(EcmOrderQuery ecmOrderQuery) {
        return vipAuthorityService.ajaxVipAuthorityList(ecmOrderQuery);
    }


}
