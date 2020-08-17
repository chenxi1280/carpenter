package com.wxcz.carpenter.controller.back;

import org.apache.shiro.authz.annotation.RequiresRoles;
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
public class HomeController {

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 跳转页面接口
     */
    @RequiresRoles("admin")
    @RequestMapping("/home")
    public String home() {
        return "back/home";
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
