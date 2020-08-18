package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by cxd
 * @Classname TestController
 * @Description TODO
 * @Date 2020/8/5 12:45
 */
@Controller
@RequestMapping("/back")
public class HomeController extends BaseController {

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 跳转页面接口
     */

    @RequestMapping("/home")
    public String home() {



        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("admin") || subject.hasRole("superadmin")){
            return "back/home";
        }

        return "error/loginError-403";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 跳转页面接口
     */
    @RequestMapping("/console")
    public String console() {
        return "back/console";
    }
}
