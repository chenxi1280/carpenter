package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Security;

/**
 * @author by cxd
 * @Classname EcmUserController
 * @Description TODO
 * @Date 2020/8/6 14:14
 */
@Controller
@RequestMapping("/back/user")
public class EcmUserController {

    @Resource
    EcmUserService ecmUserService;


    //    @RequiresRoles("art")
    @RequestMapping("/userPage")
    public String userPage() {
        return "/back/user/user-list";
    }


    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(EcmUserQuery ecmUserQuery) {

        return ecmUserService.ajaxList(ecmUserQuery);
    }


    @RequiresRoles("admin")
    @RequestMapping("chengUser")
    @ResponseBody
    public ResponseDTO chengUser(EcmUserVO ecmUserVO) {

        Subject subject = SecurityUtils.getSubject();


        if (!StringUtils.isEmpty(ecmUserVO.getRoles())) {
            if (subject.hasRole("superadmin")) {
                return ecmUserService.chengUser(ecmUserVO);
            }
            return ResponseDTO.fail("无权修改");
        }


        return ecmUserService.chengUser(ecmUserVO);
    }


}
