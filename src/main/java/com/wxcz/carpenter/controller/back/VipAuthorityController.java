package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.pojo.query.EcmVipAuthorityQuery;
import com.wxcz.carpenter.pojo.query.EcmVipRoleAuthorityQuery;
import com.wxcz.carpenter.service.AuthorityService;
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

    @Resource
    AuthorityService authorityService;


    /**
     * @param: []
     * @return: java.lang.String
     * @author: SJ
     * @Date: 2021/3/16
     * 描述 : 会员管理配置页面跳转
     */
    @RequestMapping("vipAuthority")
    public String vipAuthority() {
        return "back/vip/vipAuthority";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: SJ
     * @Date: 2021/3/16
     * 描述 : 会员管理配置页面跳转
     */
    @RequestMapping("authority")
    public String authority() {
        return "back/vip/authority";
    }

    @RequestMapping("ajaxAuthorityList")
    @ResponseBody
    public PageDTO ajaxAuthorityList(EcmVipAuthorityQuery ecmVipAuthorityQuery) {
        return authorityService.ajaxAuthorityList(ecmVipAuthorityQuery);
    }

    @RequestMapping("ajaxVipAuthorityList")
    @ResponseBody
    public PageDTO ajaxVipAuthorityList(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery) {
        return vipAuthorityService.ajaxVipAuthorityList(ecmVipRoleAuthorityQuery);
    }

    @RequestMapping("addVipRoleAuthority")
    @ResponseBody
    public ResponseDTO addVipRoleAuthority(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery) {
        return vipAuthorityService.addVipRoleAuthority(ecmVipRoleAuthorityQuery);
    }

}
