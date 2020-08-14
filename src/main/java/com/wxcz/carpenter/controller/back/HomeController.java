package com.wxcz.carpenter.controller.back;

import org.apache.shiro.SecurityUtils;
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

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 退出页面
     */
    @RequestMapping("/logoutPage")
    public String logout() {
        return "login";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 退出
     */
    @RequestMapping("/logout")
    String logoutApp() {
        SecurityUtils.getSubject().logout();
        return "loginPage";
    }


}
