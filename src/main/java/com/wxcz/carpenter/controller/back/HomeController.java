package com.wxcz.carpenter.controller.back;

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

    @RequestMapping("/home")
    public String home(){
        return "/back/home";
    }

    @RequestMapping("/console")
    public String console(){
        return "/back/console";
    }



}
